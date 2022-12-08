package com.example.travelagency.web;

import com.example.travelagency.dto.CommentDTO;
import com.example.travelagency.dto.DestinationSearchDTO;
import com.example.travelagency.services.CommentService;
import com.example.travelagency.services.ForumService;
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
    private Long forumId;

    public ForumController(ForumService forumService, CommentService commentService) {
        this.forumService = forumService;
        this.commentService = commentService;
    }

    @GetMapping("/forums")
    public String forums(Model model){

        model.addAttribute("forums", forumService.getForums());

        return "forums";
    }

    @GetMapping("/forums/{id}")
    public String forum(@PathVariable Long id, Model model){

//        if(!model.containsAttribute("commentDTO")) {
//            model.addAttribute("commentDTO", commentDTO);
//        }, @Valid CommentDTO commentDTO

        model.addAttribute("forum", forumService.getForumById(id));
        model.addAttribute("comments", commentService.getCommentsByForumId(id));
        forumId = id;

        return "forum-comments";
    }

    @PostMapping("/add-comment")
    public String addComment(@Valid CommentDTO commentDTO){

        String redirect = "redirect:/forums/"+forumId;

        commentService.addComment(commentDTO);

        return redirect;
    }

    @ModelAttribute
    public CommentDTO commentDTO(){
        return new CommentDTO();
    }
}
