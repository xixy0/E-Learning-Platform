package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.AssignmentDTO;
import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Assignment;
import com.internshipProject1.LearningPLatform.Entity.AssignmentSubmission;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Repository.AssignmentRepository;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Service.AssignmentService;
import com.internshipProject1.LearningPLatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Assignment addAssignment(Long courseId, AssignmentDTO assignmentDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        Assignment assignment = new Assignment();
        assignment.setAssignmentTitle(assignmentDTO.getAssignmentTitle());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        assignment.setCourse(course);
        assignment.setAssignmentPdfUrl(new ArrayList<String>());
        assignment.setAssignmentSubmissions(new ArrayList<>());
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->new RuntimeException("Assignment not found"));
        assignment.setAssignmentTitle(assignmentDTO.getAssignmentTitle());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        return assignmentRepository.save(assignment);
    }

    @Override
    public void deleteAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->new RuntimeException("Assignment not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    public List<AssignmentDTO> getAll() {
        List<AssignmentDTO> assignmentDTOList = new ArrayList<>();
        List<Assignment> assignments = assignmentRepository.findAll();

        for(Assignment assignment : assignments){
            assignmentDTOList.add(new AssignmentDTO(assignment.getAssignmentId(),
                    assignment.getAssignmentTitle(), assignment.getAssignmentDescription(),
                    assignment.getCourse().getCourseId(),assignment.getAssignmentPdfUrl()));
        }

        return assignmentDTOList;
    }

    @Override
    public String uploadAssignmentPdf(Long assignmentId, MultipartFile file) {
        final String ASSIGNMENT_DIR = "/assignments/pdfs/";
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()-> new RuntimeException("Assignment not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        try{
            if(file.isEmpty()){
                return "File is empty";
            }
            String uploadAssignment = System.getProperty("user.dir") + File.separator +"assignments"+ File.separator+ "pdfs";
            File directory = new File(uploadAssignment);
            if(!directory.exists()) directory.mkdirs();

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if(originalFilename != null && originalFilename.contains(".")){
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String safeFilename = "assignment_" + assignmentId + "_" + System.currentTimeMillis() + extension;
            File destinationFile = new File(directory, safeFilename);
            file.transferTo(destinationFile);

            String relativePath = "/assignments/pdfs/" + safeFilename;
            assignment.getAssignmentPdfUrl().add(relativePath);
            assignmentRepository.save(assignment);
            return relativePath;

        }catch (IOException ex){
            throw  new RuntimeException("Upload failed");
        }
    }

    @Override
    public void removeAssignmentPdf(Long assignmentId, String path) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()-> new RuntimeException("Assignment not found"));
        if(!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        List<String> paths = assignment.getAssignmentPdfUrl();
        for(String path1 : paths){
            if(path.equalsIgnoreCase(path1)){
                paths.remove(path1);
            }
        }
        assignmentRepository.save(assignment);
    }

    @Override
    public List<AssignmentSubmissionDTO> getAllCourseAssignmentSubmissions(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new RuntimeException("Assignment not found"));
        if (!userService.getLoggedInUser().getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())) {
            throw new RuntimeException("Unauthorized Instructor");
        }
        List<AssignmentSubmission> assignmentSubmissions = assignment.getAssignmentSubmissions();
        List<AssignmentSubmissionDTO> assignmentSubmissionDTOList = new ArrayList<>();
        for (AssignmentSubmission assignmentSubmission : assignmentSubmissions) {
            assignmentSubmissionDTOList.add(new AssignmentSubmissionDTO(assignmentSubmission.getAssignmentSubmissionId(),
                    assignmentSubmission.getSubmissionDate(), assignmentSubmission.getAssignmentSubmissionUrl(),
                    assignmentSubmission.getUsers().getUserId(),
                    assignmentSubmission.getAssignment().getAssignmentId()));

        }
        return assignmentSubmissionDTOList;
    }
}



