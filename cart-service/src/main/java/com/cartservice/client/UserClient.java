package com.cartservice.client;
import com.cartservice.dto.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="userservice",url = "http://localhost:8090")
public interface UserClient {
        @GetMapping("/getuser/{id}")
        Users getUserById(@PathVariable Integer id);

}
