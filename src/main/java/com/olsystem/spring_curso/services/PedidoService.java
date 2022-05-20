package com.olsystem.spring_curso.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olsystem.spring_curso.domain.ItemPedido;
import com.olsystem.spring_curso.domain.PagamentoComBoleto;
import com.olsystem.spring_curso.domain.Pedido;
import com.olsystem.spring_curso.domain.enums.EstadoPagamento;
import com.olsystem.spring_curso.repositories.ItemPedidoRepository;
import com.olsystem.spring_curso.repositories.PagamentoRepository;
import com.olsystem.spring_curso.repositories.PedidoRepository;
import com.olsystem.spring_curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired	
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired 
	private ClienteService clienteService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! ID: " + id + ", Tipo: " + Pedido.class.getName()));

	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
	    obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
	    obj.getPagamento().setPedido(obj);
	    if (obj.getPagamento() instanceof PagamentoComBoleto) {
	    	PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
	    	boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
	    	boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
	    }
	    
	   obj = repo.save(obj);
	   pagamentoRepository.save(obj.getPagamento());
	   for(ItemPedido ip: obj.getItens()) {
		   ip.setDesconto(0.0);
		   ip.setProduto(produtoService.find(ip.getProduto().getId()));
		   ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
		   ip.setPedido(obj);
	   }
	   itemPedidoRepository.saveAll(obj.getItens());
	   return obj;
	    
	}


}
