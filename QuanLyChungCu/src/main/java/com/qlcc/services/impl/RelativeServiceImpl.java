/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.qlcc.pojo.Relative;
import com.qlcc.repositories.RelativeRepository;
import com.qlcc.services.RelativeService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class RelativeServiceImpl implements RelativeService {

    @Autowired
    private RelativeRepository relativeRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public void addOrUpdate(Relative relative) {
        if (relative.getFile() != null && !relative.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(relative.getFile().getBytes(), ObjectUtils.asMap("folder", "quanlychungcu"));
                relative.setAvatar(res.get("secure_url").toString());

            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        relativeRepo.addOrUpdate(relative);

    }

    @Override
    public List<Relative> getRelative(Map<String, String> params) {
        return relativeRepo.getRelative(params);
    }

    @Override
    public Relative getRelativeById(int id) {
        return relativeRepo.getRelativeById(id);
    }

    @Override
    public void deleteRelative(int id) {
        relativeRepo.deleteRelative(id);
    }

}
