package pro.sky.exever.hogwarts.school.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.exever.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.exever.hogwarts.school.model.Avatar;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.AvatarRepository;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService extends SimpleServiceImpl<Avatar, AvatarRepository> {
    private final String avatarsDir;
    private final int thumbnailHeight;
    private final String thumbnailFormat;
    private final String thumbnailMIME;
    private final String avatarFormat;
    AvatarRepository avatarRepository;
    StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         @Value("${path.to.avatars}") String avatarsDir,
                         @Value("${avatar.format}") String avatarFormat,
                         @Value("${thumbnail.height}") int thumbnailHeight,
                         @Value("${thumbnail.format}") String thumbnailFormat,
                         @Value("${thumbnail.MIME}") String thumbnailMIME) {
        super(avatarRepository);
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarsDir = avatarsDir;
        this.thumbnailHeight = thumbnailHeight;
        this.thumbnailFormat = thumbnailFormat;
        this.thumbnailMIME = thumbnailMIME;
        this.avatarFormat = avatarFormat;
    }

    public void upload(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
        Avatar avatar = avatarRepository.findById(studentId).orElseGet(Avatar::new);
        Path filePath = Path.of(avatarsDir, studentId + "." + StringUtils.getFilenameExtension(avatarFile.getOriginalFilename()));
        if (!new File(avatarsDir).exists()) {
            Files.createDirectories(filePath.getParent());
        }
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is);
             BufferedOutputStream bos = new BufferedOutputStream(os);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ByteArrayOutputStream baos2 = new ByteArrayOutputStream()
        ) {
            BufferedImage avatarBufferedImage = ImageIO.read(bis);
            ImageIO.write(avatarBufferedImage, avatarFormat, bos);
            ImageIO.write(avatarBufferedImage, avatarFormat, baos);
            long avatarSize = baos.toByteArray().length;
            //making avatarette for saving in DB... it's like a cigarette, but avatarette :)... :|
            Image avataretteImage = avatarBufferedImage.getScaledInstance(-1, thumbnailHeight, Image.SCALE_SMOOTH);
            BufferedImage avataretteBufferedImage = new BufferedImage(
                    avataretteImage.getWidth(null),
                    avataretteImage.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = avataretteBufferedImage.createGraphics();
            g.drawImage(avataretteImage, 0, 0, null);
            g.dispose();
            ImageIO.write(avataretteBufferedImage, thumbnailFormat, baos2);

            avatar.setStudent(student);
            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(avatarSize);
            avatar.setMediaType(thumbnailMIME);
            avatar.setData(baos2.toByteArray());
            avatarRepository.save(avatar);
        }
    }

    public Avatar findByStudent_Id(Long studentId) {
        return avatarRepository.findByStudent_Id(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
    }
}
