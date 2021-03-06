package com.springapplication.controller;

import com.springapplication.converter.CourseConverter;
import com.springapplication.dto.CourseDto;
import com.springapplication.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping("/list")
    public String listCourses(Model theModel){


        CourseConverter courseConvertor = new CourseConverter();
        List<CourseDto> courseDtoList=courseConvertor.entityToDto(courseService.findAllCourses());

        theModel.addAttribute("courses",courseDtoList);

        return "course/list-courses";

    }


    @GetMapping("/showFormForAdd")
    public String showFormForAddCourse(Model theModel) {

        CourseDto courseDto=new CourseDto();

        theModel.addAttribute("course", courseDto);

        return "course/course-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdateCourse(@RequestParam("courseId") int theId,
                                    Model theModel) throws Exception {


        CourseDto courseDto=new CourseConverter().entityToDto(courseService.findCourseById(theId));

        theModel.addAttribute("course", courseDto);


        return "course/course-form";
    }



    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") CourseDto courseDto,
                              BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "course/course-form";
        }

        CourseConverter courseConverter=new CourseConverter();

        courseService.saveCourse(courseConverter.dtoToEntity(courseDto));


        return "redirect:/courses/list";
    }

    @PostMapping("/delete")
    public String deleteCourse(@RequestParam("courseId") int theId){

        courseService.deleteCourseById(theId);

        return "redirect:/courses/list";
    }



}
