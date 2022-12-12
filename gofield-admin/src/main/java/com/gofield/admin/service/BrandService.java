package com.gofield.admin.service;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.AdminListDto;
import com.gofield.admin.dto.BrandDto;
import com.gofield.admin.dto.BrandListDto;
import com.gofield.admin.dto.response.projection.AdminInfoProjectionResponse;
import com.gofield.admin.dto.response.projection.BrandInfoProjectionResponse;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.AdminRepository;
import com.gofield.domain.rds.domain.admin.AdminRole;
import com.gofield.domain.rds.domain.admin.AdminRoleRepository;
import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import com.gofield.domain.rds.domain.item.Brand;
import com.gofield.domain.rds.domain.item.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public BrandListDto getBrandList(String name, Pageable pageable) {
        Page<Brand> page = brandRepository.findAllByKeyword(name, pageable);
        List<BrandInfoProjectionResponse> projectionResponse = BrandInfoProjectionResponse.of(page.getContent());
        return BrandListDto.of(projectionResponse, page);
    }

    @Transactional
    public void updateBrand(BrandDto brandDto){
        Brand brand = brandRepository.findByBrandId(brandDto.getId());
        brand.update(brandDto.getName(), brandDto.getThumbnail(), brandDto.getIsVisible());
    }

    @Transactional(readOnly = true)
    public BrandDto getBrand(Long id){
        return BrandDto.of(brandRepository.findByBrandId(id));
    }

    @Transactional
    public void save(BrandDto brandDto){
        Brand brand = brandRepository.findByName(brandDto.getName());
        if(brand!=null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 존재하는 브랜드입니다.");
        }
        brand = Brand.newInstance(brandDto.getName(), brandDto.getThumbnail(), brandDto.getIsVisible());
        brandRepository.save(brand);
    }

    @Transactional
    public void delete(Long id){
        Brand brand = brandRepository.findByBrandId(id);
        brand.delete();
    }

}