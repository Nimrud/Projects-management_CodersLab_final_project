package pl.jaczewski.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jaczewski.beneficiaries.Beneficiary;
import pl.jaczewski.beneficiaries.BeneficiaryService;
import pl.jaczewski.coordinator.Coordinator;
import pl.jaczewski.coordinator.CoordinatorService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;
    private BeneficiaryService benService;
    private CoordinatorService coordService;

    @Autowired
    public ProjectController(ProjectService projectService, BeneficiaryService benService, CoordinatorService coordService) {
        this.projectService = projectService;
        this.benService = benService;
        this.coordService = coordService;
    }

    @GetMapping("/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        return "project";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute @Valid Project project, BindingResult result) {
        if (result.hasErrors()) {
            return "project";
        }
        projectService.saveProject(project);
        return "redirect:list";
    }

    @GetMapping(value = "/list", produces = "text/html; charset=UTF-8")
    public String projectsList(Model model){
        List<Project> projects = projectService.findAllProjects();
        model.addAttribute("projects", projects);
        return "projectsList";
    }

    @ModelAttribute("programmes")
    public List<String> programmes(){
        return Arrays.asList("PL02", "PL03", "PL04");
    }

    @ModelAttribute("outcome")
    public List<String> outcome(){
        return Arrays.asList("EOG", "NMF", "OZE", "FMG", "EE", "FWD", "1", "2", "3", "4", "[brak]");
    }

    @ModelAttribute("region")
    public List<String> region(){
        return Arrays.asList("cały kraj", "dolnośląskie", "kujawsko-pomorskie", "lubelskie", "lubuskie", "łódzkie", "małopolskie", "mazowieckie", "opolskie", "podkarpackie", "podlaskie", "pomorskie", "śląskie", "świętokrzyskie", "warmińsko-mazurskie", "wielkopolskie", "zachodniopomorskie");
    }

    @ModelAttribute("beneficiaries")
    public List<Beneficiary> getBeneficiaries(){
        return benService.findAllBeneficiaries();
    }

    @ModelAttribute("coordinators")
    public List<Coordinator> getCoordinators(){
        return coordService.findAllCoordinators();
    }
}
