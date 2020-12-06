package br.com.planilha.gastos.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<TransactionDto> buscarTransacoes(@RequestHeader("Authorization") String token, 
			@RequestParam(name = "since", required = false) String date, 
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "quantity", required = false) Integer quantity) {
		List<Transaction> transactions = new ArrayList<>();
		if(page == null || page < 0) {
			page = 0;
		}
		if(date != null && !date.isBlank() && quantity != null && quantity > 0) {
			transactions = transactionService.findSinceDateByQuantity(token, date, quantity, page);
		} else if(date != null) {
			transactions = transactionService.findSinceDate(token, date);
		} else if(quantity != null && quantity > 0) {
			transactions = transactionService.findByQuantity(token, quantity, page);
		} else {
			transactions = transactionService.findAll(token);
		}
		
		return transactionParse.toTransactionsDto(transactions);
	}
	
	@GetMapping("/find/{id}")
	public TransactionDto buscarTransacao(@RequestHeader("Authorization") String token, @PathVariable(value = "id", required = true) String idTransacao) {
		return transactionParse.toTransactionDto(transactionService.find(token, idTransacao));
	}
	
}
