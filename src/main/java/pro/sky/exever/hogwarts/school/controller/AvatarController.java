package pro.sky.exever.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.exever.hogwarts.school.model.Avatar;
import pro.sky.exever.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/student")
public class AvatarController {
    AvatarService avatarService;

    protected AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @Operation(summary = "Convert to jpg. Save to file and save small-scale thumbnail to db")
    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.upload(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Read avatarette from db")
    @GetMapping(value = "/{studentId}/avatarette", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> getAvatarPreviewFromDB(@PathVariable Long studentId) {
        Avatar avatar;
        HttpHeaders headers = new HttpHeaders();
        avatar = avatarService.findByStudent_Id(studentId);
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @Operation(summary = "Read avatar from file")
    @GetMapping(value = "/{studentId}/avatar", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> getAvatarFromFS(@PathVariable Long studentId) {
        try {
            Avatar avatar;
            HttpHeaders headers = new HttpHeaders();
            avatar = avatarService.findByStudent_Id(studentId);
            byte[] image = Files.readAllBytes(Paths.get(avatar.getFilePath()));
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(image.length);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать аватарку из файла");
        }
    }
}
