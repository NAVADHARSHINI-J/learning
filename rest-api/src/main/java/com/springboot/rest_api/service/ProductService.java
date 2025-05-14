package com.springboot.rest_api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest_api.dto.ChartDto;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.model.Category;
import com.springboot.rest_api.model.Product;
import com.springboot.rest_api.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ChartDto chartDto;
	
	public Product add(Product product) {
		return productRepository.save(product);
	}

	/*we can do it by findByCategoryId or by using streams*/
	public List<Product> getByCategory(int catId, Pageable pageable) {
	    return productRepository.findByCategoryId(catId, pageable);
	}

	/*we can do it by findByVendorId or by using streams*/
	public List<Product> getByVendor(int vId, Pageable pageable) {
		return productRepository.findAll(pageable).getContent()
						.stream().filter(v->(v.getVendor().getId()) == vId)
						.toList();
	}

	public Product getById(int pId) throws InvalidIdException {
	    Optional<Product> op=productRepository.findById(pId);
	    if(op.isEmpty())
	    	throw new InvalidIdException("Product Id is Invalid");
		return op.get();
	}

	public Product uploadImage(MultipartFile file, int pid) throws InvalidIdException, IOException {
		/*check if pid isvalid */
 		Product product = productRepository.findById(pid)
 				.orElseThrow(()->new InvalidIdException("Invalid PID given.."));
 		
 		List<String> allowedExtensions = Arrays.asList("png","jpg","jpeg","gif","svg"); 
 		String originalFileName = file.getOriginalFilename(); 
 		System.out.println(originalFileName);
 		String extension= originalFileName.split("\\.")[1];
 		/*Check weather extension is allowed or not */
 		if( !(allowedExtensions.contains(extension))) {
 			throw new RuntimeException("Image Type Invalid");
 		}
 		
 		
 		String uploadPath= "E:\\SpringBoot Projects\\UI\\react-ui\\public\\images";
 		
 		/*Create directory *///Check if directory is present else create it
 		Files.createDirectories(Paths.get(uploadPath));
 		/*Define full path with folder and image name */
 		Path path = Paths.get(uploadPath + "\\" +originalFileName); 
 		/*Copy the image into uploads path */
 		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
 		/*Save this path in Db */
 		product.setImageUrl(path.toString());
 		return productRepository.save(product);
	}

	public ChartDto getChartData() {
		//get the products list
		List<Product> list=productRepository.findAll();
		//get the category list from product
		List<Category> category=list.stream().map(l->l.getCategory()).distinct().toList();
		
		//get the count 
		Map<String,Integer> map=new HashMap<>();
		for(Category c:category) {
			int num = list.stream().filter(c1->c1.getCategory().getId()==c.getId()).toList().size();
			System.out.println(num);
			map.put(c.getName(),num);
		}
		//give the output 
		 Set<String> lables =map.keySet();
		 Collection<Integer> datas=map.values(); 
		 chartDto.setLables(lables);
		 chartDto.setDatas(datas);
		return chartDto;
	}
	
}
