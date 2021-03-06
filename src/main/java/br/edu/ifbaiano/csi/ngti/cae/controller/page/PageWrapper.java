package br.edu.ifbaiano.csi.ngti.cae.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		String httpUrl = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString().replaceAll("\\+", "%20");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
	}
	
	public Long getTotalRegistros(){
		return page.getTotalElements();
	}
	
	public List<T> getConteudo(){
		return page.getContent();
	}
	
	public boolean isVazia(){
		return page.getContent().isEmpty();
	}
	
	public int getAtual(){
		return page.getNumber();
	}
	
	public boolean isPrimeira(){
		return page.isFirst();
	}
	
	public int getTotal(){
		return page.getTotalPages();
	}
	
	public boolean isUltima(){
		return page.isLast();
	}
	
	public boolean isUnicaPagina() {
		return getTotal() < 2;
	}
	
	public String urlParaPagina(int pagina){
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String propiedade){
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder.fromUriString(this.uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propiedade, inverterDirecao(propiedade));
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	public String inverterDirecao(String propiedade){
		String direcao = "asc";
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propiedade): null;
		if (order != null){
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}
		return direcao;
	}
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null; 
		
		if (order == null) {
			return false;
		}
		
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}
