package pro.sky.exever.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/port")
public class InfoController {
    private final int serverPort;

    public InfoController(@Value("${server.port}") int serverPort) {
        this.serverPort = serverPort;
    }

    @GetMapping
    public int getPort(){
        return serverPort;
    }
}
