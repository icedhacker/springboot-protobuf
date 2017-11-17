package com.mycompany.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


@RestController
public class HttpController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ImageRepository imageRepository;

    /**
     * {@code curl -v http://localhost:8080/players/1}
     */
    @GetMapping("/players/{id}")
    PlayerProtos.Player player(@PathVariable Integer id) {
        return playerRepository.findById(id);
    }

    /**
     * {@code curl -v http://localhost:8080/players}
     */
    @GetMapping("/players")
    @ResponseBody
    PlayerProtos.PlayerList findAll() {
        return playerRepository.findAll();
    }

    @PostMapping("/uploadImg")
    void uploadImg(HttpServletRequest request) {
        try {
            ImageTest.Data data = ImageTest.Data.parseFrom(request.getInputStream());
            imageRepository.uploadImage(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/addPlayer")
    String addPlayer(HttpServletRequest request) {
        try {
            PlayerProtos.Player player = PlayerProtos.Player.parseFrom(request.getInputStream());
            System.out.println(player);
            System.out.println(player.getFullName());
            return player.getFullName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用httpclient测试，需要设置content-Type
     * httppost.addHeader("Content-Type", "application/x-protobuf;charset=utf-8");
     * @param player
     */
    @PostMapping(value = "/addPlayer1")
//    @PostMapping(value = "/addPlayer1", consumes = {"application/x-protobuf"}, produces = "application/x-protobuf")
    void addPlayer1(@RequestBody PlayerProtos.Player player) {
        System.out.println(player.getFullName());
    }
}
