package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Assignment;
import com.internshipProject1.LearningPLatform.Entity.AssignmentSubmission;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Repository.AssignmentRepository;
import com.internshipProject1.LearningPLatform.Repository.AssignmentSubmissionRepository;
import com.internshipProject1.LearningPLatform.Service.AssignmentSubmissionService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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


    @Override
    public AssignmentSubmission addAssignmentSubmission(Long assignmentId, AssignmentSubmissionDTO assignmentSubmissionDTO) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->new RuntimeException("Assignment not found"));
        List<Course> courses = userService.getLoggedInUser().getCourses();
        boolean c = false;
        for(Course course : courses){
            if(Objects.equals(course.getCourseId(),assignment.getCourse().getCourseId())) {
                c=true;
                break;
            }
        }
        if(!c){
            throw new RuntimeException("Student not enrolled");
        }
        AssignmentSubmission assignmentSubmission = new AssignmentSubmission();
        assignmentSubmission.setAssignment(assignment);
        assignmentSubmission.setSubmissionDate(LocalDateTime.now());
        assignmentSubmission.setUsers(userService.getLoggedInUser());
        assignmentSubmission.setAssignmentSubmissionUrl(uploadAssignmentSubmission(assignmentId,assignmentSubmissionDTO.getFile()));
        return assignmentSubmissionRepository.save(assignmentSubmission);


    }

    @Override
    public void deleteAssignmentSubmission(Long assignmentSubmissionId) {
       AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(assignmentSubmissionId).orElseThrow(
               ()-> new RuntimeException("No submission for the assignment")
       );
       if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN") &&
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

    @Override
    public AssignmentSubmission getAssignmentSubmissionByUser(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()-> new RuntimeException("Assignment not found"));
        List<AssignmentSubmission> assignmentSubmissions = assignment.getAssignmentSubmissions();

        for(AssignmentSubmission assignmentSubmission : assignmentSubmissions){
            if(Objects.equals(assignmentSubmission.getUsers().getUserId(),userService.getLoggedInUser().getUserId())){
                return assignmentSubmission;
            }
        }
        return null;
    }

    @Override
    public List<AssignmentSubmission> getAll() {
       return assignmentSubmissionRepository.findAll();
    }



}
