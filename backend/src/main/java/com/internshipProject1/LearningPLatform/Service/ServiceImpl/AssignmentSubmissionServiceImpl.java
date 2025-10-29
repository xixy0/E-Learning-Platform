package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Assignment;
import com.internshipProject1.LearningPLatform.Entity.AssignmentSubmission;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.AssignmentRepository;
import com.internshipProject1.LearningPLatform.Repository.AssignmentSubmissionRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.AssignmentSubmissionService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    @CacheEvict(value={"assignmentSubmissions","assignmentSubmissionUser","assignmentsAssignmentSubmissions"},allEntries = true)
    public AssignmentSubmission addAssignmentSubmission(Long assignmentId, AssignmentSubmissionDTO assignmentSubmissionDTO) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->new RuntimeException("Assignment not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();
        List<Course> courses = users.getCourses();
        boolean c = false;
        if(userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")) c=true;
        else {
            for (Course course : courses) {
                if (Objects.equals(course.getCourseId(), assignment.getCourse().getCourseId())) {
                    c = true;
                    break;
                }
            }
        }
        if(!c){
            throw new RuntimeException("Student not enrolled");
        }
        AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
        assignmentSubmission.setAssignment(assignment);
        assignmentSubmission.setSubmissionDate(LocalDateTime.now());
        assignmentSubmission.setUsers(users);
        assignmentSubmission.setAssignmentSubmissionUrl(uploadAssignmentSubmission(assignmentId,assignmentSubmissionDTO.getFile()));
        return assignmentSubmissionRepository.save(assignmentSubmission);
    }

    @Override
    @CacheEvict(value = {"assignmentSubmissions","assignmentSubmissionUser","assignmentsAssignmentSubmissions"},allEntries = true)
    public void deleteAssignmentSubmission(Long assignmentSubmissionId) {
       AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(assignmentSubmissionId).orElseThrow(
               ()-> new RuntimeException("No submission for the assignment")
       );
       if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN") &&
               !Objects.equals(userService.getLoggedInUser().getUserId(),assignmentSubmission.getUsers().getUserId())){
           throw new RuntimeException("Unauthorized User");
       }
       assignmentSubmissionRepository.deleteById(assignmentSubmissionId);
    }

    @Override
    public String uploadAssignmentSubmission(Long assignmentId ,MultipartFile file) {
        final String ASSIGNMENTSUBMISSON_DIR = "/assignmentSubmissions/pdfs/";
        try{
            if(file.isEmpty()){
                return "File is empty";
            }
            String uploadAssignment = System.getProperty("user.dir") + File.separator +"assignment" + File.separator+"submission"+File.separator+ "pdfs";
            File directory = new File(uploadAssignment);
            if(!directory.exists()) directory.mkdirs();

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if(originalFilename != null && originalFilename.contains(".")){
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String safeFilename = "assignmentSubmission_for_"+assignmentId +"_by_"+ userService.getLoggedInUser().getUserId() +"_"+ System.currentTimeMillis() + extension;
            File destinationFile = new File(directory, safeFilename);
            file.transferTo(destinationFile);

            return "/assignments/pdfs/" + safeFilename;

        }catch (IOException ex){
            throw  new RuntimeException("Upload failed");
        }
    }

    //need to delete unwanted files
    @Override
    @Cacheable(value = "assignmentSubmissionUser",key="'Assignment wise:'+ #assignmentId")
    public AssignmentSubmissionDTO getAssignmentSubmissionByUser(Long assignmentId) {
       List<AssignmentSubmissionDTO> userAssignmentSubmissionList = userService.getAllStudentAssignmentSubmissionsByAdmin(userService.getLoggedInUser().getUserId());
       for(AssignmentSubmissionDTO assignmentSubmissionDTO : userAssignmentSubmissionList){
           if(Objects.equals(assignmentSubmissionDTO.getAssignmentId(),assignmentId)){
               return assignmentSubmissionDTO;
           }
       }
       throw new RuntimeException("No submission by the user");
    }

    @Override
    @Cacheable(value = "assignmentSubmissions",key = "'all'")
    public List<AssignmentSubmissionDTO> getAll() {
       List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionRepository.findAll();
       List<AssignmentSubmissionDTO> assignmentSubmissionDTOList = new ArrayList<>();
       for(AssignmentSubmission assignmentSubmission:assignmentSubmissions){
           assignmentSubmissionDTOList.add(new AssignmentSubmissionDTO(
                   assignmentSubmission.getAssignmentSubmissionId(),
                   assignmentSubmission.getSubmissionDate(),
                   assignmentSubmission.getAssignmentSubmissionUrl(),
                   assignmentSubmission.getUsers().getUserId(),
                   assignmentSubmission.getAssignment().getAssignmentId()
           ));
       }
       return assignmentSubmissionDTOList;
    }

    @Override
    @Cacheable(value = "assignmentSubmissions",key="#assignmentSubmissionId")
    public AssignmentSubmissionDTO getAssignmentSubmissionById(Long assignmentSubmissionId) {
        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(assignmentSubmissionId).orElseThrow(
                ()-> new RuntimeException("No submission for the assignment")
        );
        return new AssignmentSubmissionDTO(
                assignmentSubmission.getAssignmentSubmissionId(),
                assignmentSubmission.getSubmissionDate(),
                assignmentSubmission.getAssignmentSubmissionUrl(),
                assignmentSubmission.getUsers().getUserId(),
                assignmentSubmission.getAssignment().getAssignmentId()
        );
    }
}
