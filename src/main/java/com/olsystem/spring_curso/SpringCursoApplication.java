package com.olsystem.spring_curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.olsystem.spring_curso.domain.Categoria;
import com.olsystem.spring_curso.domain.Cidade;
import com.olsystem.spring_curso.domain.Cliente;
import com.olsystem.spring_curso.domain.Endereco;
import com.olsystem.spring_curso.domain.Estado;
import com.olsystem.spring_curso.domain.ItemPedido;
import com.olsystem.spring_curso.domain.Pagamento;
import com.olsystem.spring_curso.domain.PagamentoComBoleto;
import com.olsystem.spring_curso.domain.PagamentoComCartao;
import com.olsystem.spring_curso.domain.Pedido;
import com.olsystem.spring_curso.domain.Produto;
import com.olsystem.spring_curso.domain.enums.EstadoPagamento;
import com.olsystem.spring_curso.domain.enums.TipoCliente;
import com.olsystem.spring_curso.repositories.CategoriaRepository;
import com.olsystem.spring_curso.repositories.CidadeRepository;
import com.olsystem.spring_curso.repositories.ClienteRepository;
import com.olsystem.spring_curso.repositories.EnderecoRepository;
import com.olsystem.spring_curso.repositories.EstadoRepository;
import com.olsystem.spring_curso.repositories.ItemPedidoRepository;
import com.olsystem.spring_curso.repositories.PagamentoRepository;
import com.olsystem.spring_curso.repositories.PedidoRepository;
import com.olsystem.spring_curso.repositories.ProdutoRepository;

@SpringBootApplication
public class SpringCursoApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itempedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringCursoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 8.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3670923456", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("7887324567", "7887324369"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto. 303", "Jardim", " 51190513", cli1, c1);
		Endereco e2 = new Endereco(null, "Aveninda Matos", "150", "Sala 800", "Centro", " 51195649", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("01/02/2022 12:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/04/2022 21:58"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("11/04/2022 23:59"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip3));

		itempedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
