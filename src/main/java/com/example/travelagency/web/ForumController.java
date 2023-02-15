package com.example.travelagency.web;

import com.example.travelagency.models.binding.CommentAddBindingModel;
import com.example.travelagency.models.service.CommentServiceModel;
import com.example.travelagency.services.CommentService;
import com.example.travelagency.services.ForumService;
import com.example.travelagency.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ForumController {
    private final ForumService forumService;
    private final CommentService commentService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ForumController(ForumService forumService, CommentService commentService, UserService userService, ModelMapper modelMapper) {
        this.forumService = forumService;
        this.commentService = commentService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/forums")
    public String forums(Model model){

        model.addAttribute("forums", forumService.getForums());

        return "forums";
    }

    @GetMapping("/forums/{id}")
    public String forum(@PathVariable Long id, Model model){

        model.addAttribute("forum", forumService.getForumById(id));
        model.addAttribute("comments", commentService.getCommentsByForumId(id));
        model.addAttribute("isModerator", userService.hasRole("MODERATOR", userService.getCurrentUser().getId()));

        return "forum-comments";
    }

    @PostMapping("/add-comment/{id}")
    public String addComment(@PathVariable Long id, @Valid CommentAddBindingModel commentAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        String redirect = "redirect:/forums/"+id;

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel", bindingResult);

            return redirect;
        }

        commentService.addComment(modelMapper.map(commentAddBindingModel, CommentServiceModel.class), id);

        return redirect;
    }

    @GetMapping("/delete-comment/{id}")
    public String deleteComment(@PathVariable Long id){
        if(!userService.hasRole("MODERATOR", userService.getCurrentUser().getId())){
            return "redirect:/";
        }
        Long forumId = commentService.deleteComment(id);
        return "redirect:/forums/"+forumId;
    }

    @ModelAttribute
    public CommentAddBindingModel commentAddBindingModel(){
        return new CommentAddBindingModel();
    }
}
