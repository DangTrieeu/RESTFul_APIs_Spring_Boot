package vn.hoidanit.jobhunter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

     // 🟢 Tạo User mới
     public User handleCreateUser(User user) {
        return userRepository.save(user);
    }

    // 🔴 Xóa User theo ID
    public void handleDeleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // 🔵 Lấy User theo ID
    public User handleGetUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // 🟡 Lấy danh sách tất cả Users
    public List<User> handleGetAllUser() {
        return userRepository.findAll();
    }

    // 🟠 Cập nhật User
    public User handleUpdateUser(User user) {
        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword()); 
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));
    }
    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
