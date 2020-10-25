package com.spring.token;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.spring.token.model.Token;
import com.spring.token.repositories.TokenRepository;
import com.spring.token.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class SpringbootdynamodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdynamodbApplication.class, args);
	}

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Bean
	public CommandLineRunner commandLineRunner(TokenRepository tokenRepository, AmazonDynamoDB amazonDynamoDB) {
		return args -> {
			try {
				DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

				System.out.println("Is Token table exists " + amazonDynamoDB.listTables(new ListTablesRequest()).getTableNames().indexOf("token"));
				if(amazonDynamoDB.listTables(new ListTablesRequest()).getTableNames().indexOf("token") == -1) {
					System.out.println("Inside Table creation block");

					CreateTableRequest tableRequest = dynamoDBMapper
							.generateCreateTableRequest(Token.class);
					ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L, 1L);
					tableRequest.setProvisionedThroughput(provisionedThroughput);
					/*tableRequest.getGlobalSecondaryIndexes().forEach(v -> v.setProvisionedThroughput(provisionedThroughput));
					Boolean tableWasCreatedForTest = TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
					if (tableWasCreatedForTest) {
						System.out.println("Created table {}  " + tableRequest.getTableName());
					}
					TableUtils.waitUntilActive(amazonDynamoDB, tableRequest.getTableName());
					*/
					amazonDynamoDB.createTable(tableRequest);
					System.out.println("Created Table Name  " + tableRequest.getTableName());
				}

				Token token = new Token();
				token.setAccessToken("EAAaflJuLaNIBALDohhi5GtLCvbYg0qrqBFlc4zjSmArME82C1jPiivSZAzRfACKvKD3MXOO4y16gKzCFrSkiVvBIUZCI7Ah7mAEVHUTF3tBuJowOqvmytIZAhSTKydYZBzAqNqM3wbbEPGCCaHo4AQPhmAuQgYzrHDoZAzdbq5gZDZD");
				token.setAppId("1864310473844946");
				token.setClientAccountId(4849);
				long expiresIn = 5102772;
				Date dateExpiry = DateUtils.getExpiryDate(expiresIn);
				Date currentDateAndTime = DateUtils.getCurrentDateAndTime();

				token.setDateCreated(currentDateAndTime);
				token.setDateExpiry(dateExpiry);
				token.setDateInserted(currentDateAndTime);
				token.setDateMoved(currentDateAndTime);
				token.setLoginUserId(144235);
				token.setStatus(0);
				token.setType("BMR");
				Token s = tokenRepository.save(token);
				System.out.println(s.getId()+"   token  "+ s.getAccessToken());
				Iterable<Token> it = tokenRepository.findAll();


				it.forEach(new Consumer<Token>() {
					int i = 0;
					@Override
					public void accept(Token token) {
						System.out.println(token.getAccessToken());
						System.out.println(token.getId());
						System.out.println(i);
						i++;
					}
				});


				//dynamoDBMapper.batchDelete((List<Token>) tokenRepository.findAll());


				/*if(amazonDynamoDB.listTables(new ListTablesRequest()).getTableNames().indexOf("token") <= 0) {
					DeleteTableRequest dtr = dynamoDBMapper.generateDeleteTableRequest(Token.class);
					TableUtils.deleteTableIfExists(amazonDynamoDB, dtr);
					System.out.println("Deleted table {}"+ dtr.getTableName());
				}*/

				System.out.println("end  "+amazonDynamoDB.listTables(new ListTablesRequest()).getTableNames().indexOf("token"));



			} catch (Exception e) {
				e.printStackTrace();

			}
		} ;

	}

}
