package com.gabrielarodrigues.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabrielarodrigues.cursomc.domain.Categoria;
import com.gabrielarodrigues.cursomc.domain.Cidade;
import com.gabrielarodrigues.cursomc.domain.Cliente;
import com.gabrielarodrigues.cursomc.domain.Endereco;
import com.gabrielarodrigues.cursomc.domain.Estado;
import com.gabrielarodrigues.cursomc.domain.ItemPedido;
import com.gabrielarodrigues.cursomc.domain.Pagamento;
import com.gabrielarodrigues.cursomc.domain.PagamentoComBoleto;
import com.gabrielarodrigues.cursomc.domain.PagamentoComCartao;
import com.gabrielarodrigues.cursomc.domain.Pedido;
import com.gabrielarodrigues.cursomc.domain.Produto;
import com.gabrielarodrigues.cursomc.domain.enums.EstadoPagamento;
import com.gabrielarodrigues.cursomc.domain.enums.TipoCliente;
import com.gabrielarodrigues.cursomc.repositories.CategoriaRepository;
import com.gabrielarodrigues.cursomc.repositories.CidadeRepository;
import com.gabrielarodrigues.cursomc.repositories.ClienteRepository;
import com.gabrielarodrigues.cursomc.repositories.EnderecoRepository;
import com.gabrielarodrigues.cursomc.repositories.EstadoRepository;
import com.gabrielarodrigues.cursomc.repositories.ItemPedidoRepository;
import com.gabrielarodrigues.cursomc.repositories.PagamentoRepository;
import com.gabrielarodrigues.cursomc.repositories.PedidoRepository;
import com.gabrielarodrigues.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
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
	private ItemPedidoRepository itemPedidoRepositoty;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto (null, "Impressora", 800.00);
		Produto p3 = new Produto (null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado (null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com","123456789", TipoCliente.PESSOASFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1199999999", "1188888888"));
		
		Endereco e1 = new Endereco(null, "Rua Flores","300", "Apto 303", "Jardim", "04589000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "8989899", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		  
		  
		  Pedido ped1 = new Pedido(null, sdf.parse("08/09/2021 22:24"), cli1, e2);
		  Pedido ped2 = new Pedido(null, sdf.parse("01/07/2021 08:07"), cli1, e2);
		  
		  
		  Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO,
		  ped1, 6); ped1.setPagamento(pagto1);
		  
		  Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,
		  ped2, sdf.parse("15/12/2021 00:00"), null); ped2.setPagamento(pagto2);
		  
		  
		  cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		  
		  pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		  pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		  
		  
		  ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		  ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		  ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		  
		  ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		  ped2.getItens().addAll(Arrays.asList(ip3));
		  
		  p1.getItens().addAll(Arrays.asList(ip1));
		  p2.getItens().addAll(Arrays.asList(ip3));
		  p3.getItens().addAll(Arrays.asList(ip2));
		  
		  
		  itemPedidoRepositoty.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
