package br.com.planilha.gastos.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.planilha.gastos.adapter.TransactionControllerAdapter;
import br.com.planilha.gastos.dto.TransactionDto;
import br.com.planilha.gastos.entity.Transaction;
import br.com.planilha.gastos.parse.TransactionDeliveryParse;
import br.com.planilha.gastos.service.TransactionService;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController implements TransactionControllerAdapter {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionDeliveryParse transactionParse;
	
	@PostMapping("/register")
	public TransactionDto cadastrarTransacao(@RequestHeader("Authorization") String token, @RequestBody TransactionDto transactionDto) {
		Transaction transaction = transactionParse.toTransaction(transactionDto);
		
		return transactionParse.toTransactionDto(transactionService.register(token, transaction));
	}
	
	@GetMapping("/find")
	public List<TransactionDto> buscarTransacoes(@RequestHeader("Authorization") String token) {
		return transactionParse.toTransactionsDto(transactionService.find(token));
	}
	
	@GetMapping("/find/{id}")
	public TransactionDto buscarTransacao(@RequestHeader("Authorization") String token, @PathVariable(value = "id", required = true) String idTransacao) {
		return transactionParse.toTransactionDto(transactionService.find(token, idTransacao));
	}
	
}
