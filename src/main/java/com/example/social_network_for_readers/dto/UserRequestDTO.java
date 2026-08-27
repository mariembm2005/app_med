package com.example.social_network_for_readers.dto;

import com.example.social_network_for_readers.entity.LibraryEntry;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String confirmPassword;

    private String image;
    private List<LibraryEntryRequestDTO> libraryEntryRequestDTOList;
    private List<ReviewRequestDTO> reviewRequestDTOList;
    private List<GroupRequestDTO> groupRequestDTOList;
    private List<CollectionRequestDTO> collectionRequestDTOList;
}
