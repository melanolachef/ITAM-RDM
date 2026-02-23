package com.reidosmotores.itam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reidosmotores.itam.model.AssetPhoto;

public interface AssetPhotoRepository extends JpaRepository<AssetPhoto, Long> {
    List<AssetPhoto> findByAssetId(Long assetId);
}
