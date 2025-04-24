package org.example.outlivryteamproject.domain.menu.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.outlivryteamproject.domain.menu.dto.requestDto.MenuRequestDto;
import org.example.outlivryteamproject.domain.menu.dto.responseDto.MenuResponseDto;
import org.example.outlivryteamproject.domain.menu.entity.Menu;
import org.example.outlivryteamproject.domain.menu.repository.MenuRepository;
import org.example.outlivryteamproject.domain.store.entity.Store;
import org.example.outlivryteamproject.domain.store.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final StoreRepository storeRepository;

    private final AmazonS3 amazonS3Client;

    private final String bucketName = "janghwal-image-bucket-1";


    @Override
    @Transactional
    public MenuResponseDto createMenu(Long storeId, Long userId, MenuRequestDto menuRequestDto) {

        Store store = matchesOwner(userId, storeId);

        String imageUrl = uploadImage(menuRequestDto.getImage());

        Menu menu = new Menu(menuRequestDto, imageUrl);
        menu.setStore(store);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }

    @Override
    public MenuResponseDto modifiedMenu(Long storeId, Long userId, MenuRequestDto menuRequestDto, Long menuId) {

        Store store = matchesOwner(userId, storeId);

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(menuRequestDto.getMenuName() != null){
            findMenuById.setMenuName(menuRequestDto.getMenuName());
        }
        if(menuRequestDto.getPrice() != null){
            findMenuById.setPrice(menuRequestDto.getPrice());
        }
        if(menuRequestDto.getImage() != null){
            String imageUrl = uploadImage(menuRequestDto.getImage());
            findMenuById.setImageUrl(imageUrl);
        }
        if(menuRequestDto.getIsDepleted() != null){
            findMenuById.setIsDepleted(menuRequestDto.getIsDepleted());
        }
        if(menuRequestDto.getIsDeleted() != null){
            findMenuById.setDeleted(menuRequestDto.getIsDeleted());
        }

        return new MenuResponseDto(findMenuById);
    }

    @Override
    public void deleteMenu(Long userId, Long storeId, Long menuId) {

        Store store = matchesOwner(userId, storeId);

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        if(store != findMenuById.getStore()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        menuRepository.delete(findMenuById);
    }

    @Override
    public MenuResponseDto findMenuById(Long menuId) {

        Menu findMenuById = menuRepository.findMenuByIdOrElseThrow(menuId);

        return new MenuResponseDto(findMenuById);
    }

    @Override
    public List<MenuResponseDto> findAllMenusByStore(Long storeId) {

        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        List<Menu> menus = menuRepository.findAllByStore(store);

        return menus.stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());
    }

    // 주인인지 확인하는 함수
    private Store matchesOwner(Long userId, Long storeId){
        Store store = storeRepository.findByStoreIdOrElseThrow(storeId);

        Long storeOwnerId = store.getUser().getId();

        if(!storeOwnerId.equals(userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return store;
    }

    private String uploadImage(MultipartFile file) {
        try {
            // S3에 업로드할 파일 이름 생성 (예: UUID와 시간정보로 중복 방지)
            String fileName = UUID.randomUUID() + "-" + System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // S3 업로드 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // S3에 파일 업로드
            // file.getInputStream() 바이트 코드
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            // S3에서 파일의 URL을 반환 (버킷 + 파일 이름)
            return amazonS3Client.getUrl(bucketName, fileName).toString();

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }
}
