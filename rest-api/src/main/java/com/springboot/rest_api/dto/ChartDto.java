package com.springboot.rest_api.dto;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ChartDto {
 private Set<String> lables ;
 private  Collection<Integer> datas;
public Set<String> getLables() {
	return lables;
}
public void setLables(Set<String> lables) {
	this.lables = lables;
}
public Collection<Integer> getDatas() {
	return datas;
}
public void setDatas(Collection<Integer> datas) {
	this.datas = datas;
}
}
