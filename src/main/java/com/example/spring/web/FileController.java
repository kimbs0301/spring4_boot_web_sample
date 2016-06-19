package com.example.spring.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author gimbyeongsu
 * 
 */
@Controller
@RequestMapping("/file")
public class FileController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	@Value("#{configProperties['file.upload.path']}")
	private String fileUploadPath;

	@Autowired
	private Environment environment;

	@Autowired
	private View fileDownloadView;

	@PostConstruct
	public void init() {
		// fileUploadPath = environment.getRequiredProperty("file.upload.path");
	}

	/**
	 * curl -o a.txt "http://localhost:8080/mvc/file/a.txt/download"
	 * http://localhost:8080/mvc/file/a.txt/download
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{file}/download", method = RequestMethod.GET)
	public ModelAndView download(@PathVariable("file") String file) {
		LOGGER.debug("");
		// ModelAndView mav = new ModelAndView("fileDownloadView");
		ModelAndView mav = new ModelAndView(fileDownloadView);
		File downloadFile = new File(fileUploadPath + "/" + file);
		mav.addObject("downloadFile", downloadFile);
		return mav;
	}

	/**
	 * http://localhost:8080/mvc/file/uploadForm
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public String uploadForm(Model model) {
		File rootFolder = new File(fileUploadPath);
		List<String> fileNames = Arrays.stream(rootFolder.listFiles()).map(f -> f.getName())
				.collect(Collectors.toList());
		for (String fileName : fileNames) {
			LOGGER.debug("{}", fileName);
		}
		model.addAttribute(
				"files",
				Arrays.stream(rootFolder.listFiles()).sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
						.map(f -> f.getName()).collect(Collectors.toList()));
		return "uploadForm";
	}

	@RequestMapping(value = "/downloadForm", method = RequestMethod.GET)
	public String downloadForm(Model model) {
		return "downloadForm";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		String name = file.getOriginalFilename();
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
			return "redirect:/file/downloadForm";
		}
		if (name.contains("/")) {
			redirectAttributes.addFlashAttribute("message", "Relative pathnames not allowed");
			return "redirect:/file/downloadForm";
		}

		if (!file.isEmpty()) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileUploadPath
						+ "/" + name)));
				FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
				redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + name + "!");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload " + name + " => " + e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "You failed to upload " + name
					+ " because the file was empty");
		}
		return "redirect:/file/downloadForm";
	}
}
