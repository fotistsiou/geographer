package unipi.fotistsiou.geographer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import unipi.fotistsiou.geographer.entity.Result;
import unipi.fotistsiou.geographer.service.ResultService;

import java.util.Optional;

@Controller
public class ResultController {
    private final ResultService resultService;

    @Autowired
    public ResultController(
        ResultService resultService
    ){
        this.resultService = resultService;
    }

    @GetMapping("/result/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getResult(
            @PathVariable Long id,
            Model model
    ) {
        Optional<Result> optionalResult = resultService.getResultById(id);
        if (optionalResult.isPresent()) {
            Result result = optionalResult.get();
            model.addAttribute("result", result);
            return "result";
        } else {
            return "404";
        }
    }
}
