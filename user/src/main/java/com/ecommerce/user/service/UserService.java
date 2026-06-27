package com.ecommerce.user.service;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public String createUser(UserRequest userRequest) {

        User user = new User();
        userRepository.save(updateUserFromRequest(user, userRequest));
        return "User created Successfully";
    }

    public Optional<UserResponse> getUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();

//        for(User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return null;
    }

    public boolean updateUser(Long id, UserRequest userRequest) {

        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);

//        return userList.stream()
//                .filter(updateUser -> updateUser.getId().equals(id))
//                .findFirst()
//                .map(existingUser -> {
//                    existingUser.setFirstName(user.getFirstName());
//                    existingUser.setLastName(user.getLastName());
//                    return existingUser;
//                }).orElse(null);

//        if (userToBeChanged.isEmpty()) {
//            return null;
//        }
//        User updatedUser = userToBeChanged.get();
//        updatedUser.setFirstName(user.getFirstName());
//        updatedUser.setLastName(user.getLastName());
//
//        return updatedUser;
    }



    public UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setZip(user.getAddress().getZip());

            userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }

    public User updateUserFromRequest(User user ,UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZip(userRequest.getAddress().getZip());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
        return user;
    }

    @Transactional
    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }
}
