package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.AssignmentDTO;
import com.internshipProject1.LearningPLatform.DTO.AssignmentSubmissionDTO;
import com.internshipProject1.LearningPLatform.Entity.Assignment;
import com.internshipProject1.LearningPLatform.Entity.AssignmentSubmission;
import com.internshipProject1.LearningPLatform.Entity.Course;
import com.internshipProject1.LearningPLatform.Entity.Users;
import com.internshipProject1.LearningPLatform.Repository.AssignmentRepository;
import com.internshipProject1.LearningPLatform.Repository.CourseRepository;
import com.internshipProject1.LearningPLatform.Repository.UserRepository;
import com.internshipProject1.LearningPLatform.Service.AssignmentService;
import com.internshipProject1.LearningPLatform.Service.NotificationService;
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
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    @CacheEvict(value = {"assignments","courseAssignment"},allEntries = true)
    public Assignment addAssignment(Long courseId, AssignmentDTO assignmentDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("Course not found"));
        Users users = userRepository.findById(userService.getLoggedInUser().getUserId()).get();

        if(!users.getLogin().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(course.getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }

        Assignment assignment = new Assignment();
        assignment.setAssignmentTitle(assignmentDTO.getAssignmentTitle());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        assignment.setCourse(course);
        assignment.setSubmissionDate(LocalDateTime.now());
        assignment.setAssignmentPdfUrl(new ArrayList<>());
        assignment.setAssignmentSubmissions(new ArrayList<>());
        Assignment assignment1 = assignmentRepository.save(assignment);

        if (assignmentDTO.getFile() != null && !assignmentDTO.getFile().isEmpty()) {
            String pdfUrl = uploadAssignmentPdf(assignment1.getAssignmentId(), assignmentDTO.getFile());
            assignment1.getAssignmentPdfUrl().add(pdfUrl);
            assignmentRepository.save(assignment1);
        }

        notificationService.createAndSend(users,
                "ASSIGNMENT_ADDED" ,
                assignment1.getAssignmentTitle(),
                "Assignment added successfully",
                "Timestamp "+assignment1.getAssignmentId());

        return assignment1;
    }

    @Override
    @CacheEvict(value = {"assignments","courseAssignment"},allEntries = true)
    public Assignment updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->new RuntimeException("Assignment not found"));

        if(!assignmentDTO.getAssignmentTitle().isEmpty())
            assignment.setAssignmentTitle(assignmentDTO.getAssignmentTitle());

        if(!assignmentDTO.getAssignmentDescription().isEmpty())
            assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());

        return assignmentRepository.save(assignment);
    }

    @Override
    @CacheEvict(value = {"assignments","courseAssignment"},allEntries = true)
    public void deleteAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()->new RuntimeException("Assignment not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    @Cacheable(value = "assignments",key="'all'")
    public List<AssignmentDTO> getAll() {
        List<AssignmentDTO> assignmentDTOList = new ArrayList<>();
        List<Assignment> assignments = assignmentRepository.findAll();

        for(Assignment assignment : assignments){
            assignmentDTOList.add(new AssignmentDTO(
                    assignment.getAssignmentId(),
                    assignment.getAssignmentTitle(),
                    assignment.getAssignmentDescription(),
                    assignment.getCourse().getCourseId(),
                    assignment.getAssignmentPdfUrl()));
        }

        return assignmentDTOList;
    }

    @Override
    public String uploadAssignmentPdf(Long assignmentId, MultipartFile file) {
        final String ASSIGNMENT_DIR = "/assignments/pdfs/";
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

            return "/assignments/pdfs/" + safeFilename;

        }catch (IOException ex){
            throw  new RuntimeException("Upload failed");
        }
    }

    /*Here the String path has a hidden /n.So without trimming it .equals dont work*/
    @Override
    @CacheEvict(value = {"assignments","courseAssignment"},allEntries = true)
    public void removeAssignmentPdf(Long assignmentId, String path) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                ()-> new RuntimeException("Assignment not found"));
        if(!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())){
            throw new RuntimeException("Unauthorized Instructor");
        }
        System.out.println("[" +path+ "]");
        String trimPath = path.trim();
        System.out.println("["+trimPath+"]");
        System.out.println("Before");
        List<String> paths = assignment.getAssignmentPdfUrl();
        for(int i=0;i<paths.size();i++){
            if(Objects.equals(trimPath,paths.get(i))){
                System.out.println("Inside if");
                paths.remove(i);
                break;
            }
            System.out.println("["+paths.get(i)+"]");
        }
        System.out.println("After");
        assignmentRepository.save(assignment);
    }

    @Override
    @Cacheable(value = "assignmentsAssignmentSubmissions",key = "#assignmentId")
    public List<AssignmentSubmissionDTO> getAllCourseAssignmentSubmissions(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new RuntimeException("Assignment not found"));
        if (!userService.getLoggedInUser().getRole().equalsIgnoreCase("ADMIN")
                && !Objects.equals(assignment.getCourse().getInstructor().getUserId(), userService.getLoggedInUser().getUserId())) {
            throw new RuntimeException("Unauthorized Instructor");
        }
        List<AssignmentSubmission> assignmentSubmissions = assignment.getAssignmentSubmissions();
        List<AssignmentSubmissionDTO> assignmentSubmissionDTOList = new ArrayList<>();
        for (AssignmentSubmission assignmentSubmission : assignmentSubmissions) {
            assignmentSubmissionDTOList.add(new AssignmentSubmissionDTO(
                    assignmentSubmission.getAssignmentSubmissionId(),
                    assignmentSubmission.getSubmissionDate(),
                    assignmentSubmission.getAssignmentSubmissionUrl(),
                    assignmentSubmission.getUsers().getUserId(),
                    assignmentSubmission.getAssignment().getAssignmentId()));

        }
        return assignmentSubmissionDTOList;
    }

    @Override
    @Cacheable(value = "assignments",key = "#assignmentId")
    public AssignmentDTO getAssignmentById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new RuntimeException("Assignment not found"));
        return new AssignmentDTO(
                assignment.getAssignmentId(),
                assignment.getAssignmentTitle(),
                assignment.getAssignmentDescription(),
                assignment.getCourse().getCourseId(),
                assignment.getAssignmentPdfUrl());
    }

    @Override
    @CacheEvict(value = {"assignments","courseAssignment"},allEntries = true)
    public String addAssignmentPdf(Long assignmentId, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Users instructor = userRepository.findById(userService.getLoggedInUser().getUserId()).get();
        if (!instructor.getLogin().getRole().equalsIgnoreCase("ADMIN") &&
                !Objects.equals(assignment.getCourse().getInstructor().getUserId(), instructor.getUserId())) {
            throw new RuntimeException("Unauthorized Instructor");
        }
        String pdfUrl = uploadAssignmentPdf(assignmentId, assignmentDTO.getFile());
        assignment.getAssignmentPdfUrl().add(pdfUrl);
        assignmentRepository.save(assignment);
        notificationService.createAndSend(
                instructor,
                "ASSIGNMENT_PDF_ADDED",
                assignment.getAssignmentTitle(),
                "PDF uploaded successfully",
                pdfUrl
        );
        return pdfUrl;
    }
}



