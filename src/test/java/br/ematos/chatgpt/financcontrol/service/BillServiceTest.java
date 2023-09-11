package br.ematos.chatgpt.financcontrol.service;


import br.ematos.chatgpt.financcontrol.entity.Bill;
import br.ematos.chatgpt.financcontrol.entity.Tag;
import br.ematos.chatgpt.financcontrol.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

class BillServiceTest {

    private static List<String> STRINGS = Arrays.asList(
            "03-Aug-2023 |     Costco       | Primeira feira básica         |    Alimentação           | $ 320.45",
            "03-Aug-2023 |     Dollarama    | Variados para casa            | Utensílios domésticos     | $ 116.14",
            "03-Aug-2023 |     Metro        | Pizza + Coca para jantar      |    Alimentação           | $ 40.06",
            "03-Aug-2023 |     TimHortons   | Welcome Paula                 |    Alimentação           | $ 17.00",
            "03-Aug-2023 |     Uber         | Volta do Metro                |    Transporte            | $ 11.21",
            "04-Aug-2023 |     Shoppers     | Passagem de ônibus            |    Transporte            | $ 11.25",
            "04-Aug-2023 |     KFC          | Jantar                         |    Alimentação           | $ 16.75",
            "04-Aug-2023 |     CanadaPost   | Material para Carteira de Motorista | Transporte        | $ 92.90",
            "04-Aug-2023 |     Dollarama    | Variados para casa            | Utensílios domésticos     | $ 11.02",
            "04-Aug-2023 |     Dollarama    | Variados para casa            | Utensílios domésticos     | $ 46.85",
            "04-Aug-2023 |     NoFrios      | Cebola e alho                 |    Alimentação           | $ 3.30",
            "04-Aug-2023 |     Walmart      | Diversos                       | Cuidados com a Beleza    | $ 41.28",
            "04-Aug-2023 |     Walmart      | Produtos Pele Lorena          | Cuidados com a Beleza    | $ 11.27",
            "04-Aug-2023 |     Subway       | Almoço                         |    Alimentação           | $ 21.42",
            "04-Aug-2023 |     Uber         | Ida ao United Supermarket - Japa | Transporte            | $ 11.45",
            "04-Aug-2023 |     Uber         | FreshCo - Scotiabank          |    Transporte            | $ 14.28",
            "05-Aug-2023 |     BurgerKing   | Almoço                         |    Alimentação           | $ 31.62",
            "05-Aug-2023 |     Shoppers     | Passagem de ônibus            |    Transporte            | $ 45.00",
            "05-Aug-2023 |     Amazon       | Cabo Notebook                 |    Eletrônicos           | $ 12.31",
            "06-Aug-2023 |     NoFrios      | Feira 1                        |    Alimentação           | $ 60.48",
            "06-Aug-2023 |     Sparkle Solutions | Credito Lavagem de Roupa  |    Alimentação           | $ 30.00",
            "06-Aug-2023 |     Wendy's      | Hamb. Batata e Cafe           |    Alimentação           | $ 12.75",
            "06-Aug-2023 |     Dollarama    | Potes + Leite de Amendoa      |    Alimentação           | $ 4.48",
            "06-Aug-2023 |     S& H Health Foods | Psyllium                   |    Alimentação           | $ 10.50",
            "07-Aug-2023 |     Dollarama    | Acessorios para Roupas + cozinha + alimentos | Utensílios domésticos | $ 78.36",
            "07-Aug-2023 |     McDonalds    | Almoço                         |    Alimentação           | $ 24.50",
            "07-Aug-2023 |     Amazon       | Gabinete PC                   |    Eletrônicos           | $ 112.99",
            "07-Aug-2023 |     Beauty Supply Outlet | Shampoo e Condicionador  |    Saúde                 | $ 42.93",
            "08-Aug-2023 |     McDonalds    | Sorvetinho para ir ao banheiro |    Alimentação           | $ 2.09",
            "08-Aug-2023 |     Dollarama    | Cozinha + alimento            | Utensílios domésticos     | $ 24.30",
            "08-Aug-2023 |     IKEA         | Almoço IKEA                   |    Alimentação           | $ 29.90",
            "08-Aug-2023 |     Amazon       | Memória Note                  |    Eletrônicos           | $ 41.80",
            "08-Aug-2023 |     IKEA         | Compras pequenas (caixas, luminaria, edredon, capa, etc) | Móveis | $ 354.33",
            "08-Aug-2023 |     IKEA         | Cadeiras + Mesas (jantar e trabalho) | Móveis            | $ 1,114.17",
            "08-Aug-2023 |     IKEA         | Gaveteiro 4 pts + criado mudo  |    Móveis                | $ 371.76",
            "08-Aug-2023 |     IKEA         | Cachorro quentes              |    Alimentação           | $ 6.78",
            "08-Aug-2023 |     Jysk         | Varal de roupas               |    Móveis                | $ 33.89",
            "08-Aug-2023 |     IKEA         | Cadeiras da Sala              |    Móveis                | $ 101.70",
            "09-Aug-2023 |     FoodBasics   | Açúcar e Biscoito Maria       |    Alimentação           | $ 7.78",
            "09-Aug-2023 |     Shoppers     | Tickets de ônibus             |    Transporte            | $ 45.00",
            "09-Aug-2023 |     Shoppers     | Talco para pé                 |    Saúde                 | $ 11.52",
            "09-Aug-2023 |     Shell        | Gasolina Carro Alugado        |    Transporte            | $ 45.00",
            "09-Aug-2023 |     Entreprise Rent-A-Car | Aluguel carro para IKEA - Kona | Transporte | $ 103.27",
            "09-Aug-2023 |     Uber         | Volta para casa do Costco     |    Transporte            | $ 14.62",
            "09-Aug-2023 |     Costco       | Feira                         |    Alimentação           | $ 117.24",
            "09-Aug-2023 |     Costco       | Membership                    |    Alimentação           | $ 135.60",
            "09-Aug-2023 |     Costco       | Almoço - Costco               |    Alimentação           | $ 9.56",
            "09-Aug-2023 |     Costco       | Ninja                         | Utensílios domésticos     | $ 226.00",
            "09-Aug-2023 |     Library London | Impressão                  |    Transporte            | $ 0.20",
            "09-Aug-2023 |     Amazon       | Sanduicheira + Barbeador      | Utensílios domésticos     | $ 134.57",
            "10-Aug-2023 |     FreshCo      | Pizza + Brita                 |    Alimentação           | $ 38.12",
            "10-Aug-2023 |     United Supermarket | Verdes + Raízes         |    Alimentação           | $ 19.99",
            "10-Aug-2023 |     Dollarama    | Potes+Balde+Bowl+Carregador+Lampada | Utensílios domésticos | $ 111.06",
            "10-Aug-2023 |     Amazon       | Barbeador                     |    Saúde                 | $ 89.38",
            "11-Aug-2023 |     BurgerKing   | Almoço                         |    Alimentação           | $ 28.00",
            "11-Aug-2023 |     Dollarama    | Potes+Sacola+Pés de Cadeira+Sabão | Utensílios domésticos | $ 22.32",
            "11-Aug-2023 |     Jysk         | Colchão + Protetor            |    Móveis                | $ 1,083.67",
            "11-Aug-2023 |     HomeDepot    | Extensões                     |    Eletrônicos           | $ 83.56",
            "11-Aug-2023 |     Amazon       | Humidificador de Ar           |    Eletrônicos           | $ 101.69",
            "11-Aug-2023 |     Amazon       | Cabos para PC                 |    Eletrônicos           | $ 45.19",
            "12-Aug-2023 |     Amazon       | Suporte TV                    |    Móveis                | $ 175.05",
            "12-Aug-2023 |     Amazon       | TV                            |    Eletrônicos           | $ 756.41",
            "13-Aug-2023 |     Dollarama    | Cheetos + Mint + Descanso de Prat + Prateleira Banheira | Alimentação | $ 14.41",
            "13-Aug-2023 |     Mountain Warehouse | Vortex Longline Padded Jacket | Roupas e Acessórios | $ 151.99",
            "13-Aug-2023 |     Mountain Warehouse | Climb Womens 3 in 1 Jacket    | Roupas e Acessórios | $ 59.33",
            "13-Aug-2023 |     Mountain Warehouse | Snowdon Melange Womens Fleece | Roupas e Acessórios | $ 22.50",
            "13-Aug-2023 |     Mountain Warehouse | Montana Womens Fleece        | Roupas e Acessórios | $ 22.50",
            "13-Aug-2023 |     Mountain Warehouse | Explorer Womens Merino - Meia | Roupas e Acessórios | $ 15.00",
            "13-Aug-2023 |     Mountain Warehouse | Outdoor Walking Sock 3 Pack - Meia | Roupas e Acessórios | $ 7.99",
            "13-Aug-2023 |     McDonalds    | Café + Coca                   |    Alimentação           | $ 3.23",
            "13-Aug-2023 |     Mountain Warehouse | Flare Womens Parka Dark Green, Womens Size 10 US | Roupas e Acessórios | $ 119.99",
            "13-Aug-2023 |     Mountain Warehouse | Talus Mens Printed Thermal Top Camouflage, Large | Roupas e Acessórios | $ 12.99",
            "13-Aug-2023 |     Mountain Warehouse | IsoTherm Womens Brushed Thermal Leggings Grey, Medium | Roupas e Acessórios | $ 13.98",
            "13-Aug-2023 |     Mountain Warehouse | Mens Winter Trek Stretch Pants - Short Length Jet Black, Mens W36 | Roupas e Acessórios | $ 69.99",
            "14-Aug-2023 |     Fido         | Telefone Lorena               |    Telefone Celular        | $ 46.66",
            "14-Aug-2023 |     Rogers       | Telefone Eduardo              |    Telefone Celular        | $ 58.32",
            "14-Aug-2023 |     Rogers       | Internet                      |    Internet                | $ 83.10",
            "15-Aug-2023 |     Amazon       | Monitor                       |    Eletrônicos           | $ 280.12",
            "16-Aug-2023 |     Amazon       | Gorro                         |    Roupas e Acessórios    | $ 21.99",
            "16-Aug-2023 |     Amazon       | Adaptadores brancos           |    Eletrônicos           | $ 9.99",
            "16-Aug-2023 |     Amazon       | Luva                          |    Roupas e Acessórios    | $ 26.99",
            "16-Aug-2023 |     Amazon       | Mousepad                      |    Eletrônicos           | $ 19.99",
            "17-Aug-2023 |     FreshCo      | Pizza + Ham + Pepsi + Maçã + Banana + FrutsVermelhas | Alimentação | $ 39.34",
            "17-Aug-2023 |     Amazon       | Impressora                    |    Eletrônicos           | $ 131.63",
            "17-Aug-2023 |     Amazon       | Carrinho PC                   |    Eletrônicos           | $ 28.24",
            "19-Aug-2023 |     Costco       | Alimentação + Vazilhas + Lancheira Térmica | Alimentação | $ 173.84",
            "19-Aug-2023 |     Costco       | Almoço Costco                 |    Alimentação           | $ 12.94",
            "21-Aug-2023 |     Costco Online | Papel + Banco + Aspirador    |    Eletrônicos           | $ 229.36",
            "21-Aug-2023 |     Fanshawe Lib | Livros 1º Semestre           |    Creche ou Escola       | $ 28.25",
            "21-Aug-2023 |     Fanshawe Lib | Livros 1º Semestre           |    Creche ou Escola       | $ 117.05",
            "23-Aug-2023 |     McDonalds    | Almoço Eduardo                |    Alimentação           | $ 13.21",
            "23-Aug-2023 |     McDonalds    | Almoço Lorena                 |    Alimentação           | $ 9.50",
            "23-Aug-2023 |     Walmart      | Amendoas, Achoc., Banana, Aveia, Panela | Alimentação | $ 54.72",
            "23-Aug-2023 |     Dollarama    | Limpeza Casa, Cabides, Pringgles, Alfredo | Alimentação | $ 18.95",
            "23-Aug-2023 |     Walmart      | Almond Milk                   |    Alimentação           | $ 8.00",
            "23-Aug-2023 |     Shoppers     | Tickets de ônibus             |    Transporte            | $ 33.75",
            "25-Aug-2023 |     Mto Drivetest D34 | G1 Test - Eduardo         |    Transporte            | $ 106.00",
            "26-Aug-2023 |     Rafael Mag. - Prof. Driver | 1 Aula Lorena        |    Transporte            | $ 95.00",
            "26-Aug-2023 |     Mto Drivetest D34 | G2 Test - Eduardo         |    Transporte            | $ 91.25",
            "26-Aug-2023 |     McDonalds    | Café da manhã pós G1          |    Alimentação           | $ 11.82",
            "27-Aug-2023 |     Homestead    | Aluguel - 1 Grosvenor St.    |    Moradia                | $ 1,740.00",
            "28-Aug-2023 |     Sparkle Solutions | Credito Lavagem de Roupa  |    Alimentação           | $ 50.00",
            "28-Aug-2023 |     Dollarama    | Saco de Lixo                  |    Alimentação           | $ 1.41",
            "31-Aug-2023 |     Dollarama    | Comidas + Fuê + pano de chão  |    Alimentação           | $ 21.47",
            "31-Aug-2023 |     Walmart      | Goiabadas                     |    Alimentação           | $ 5.54",
            "31-Aug-2023 |     McDonalds    | Almoço St. Thomas             |    Alimentação           | $ 24.95",
            "31-Aug-2023 |     Darryll & Tracy's - NoFrios | Bolo + Detefon    |    Alimentação           | $ 21.56",
            "31-Aug-2023 |     Walmart      | Remédios + Protetor           |    Saúde                 | $ 13.23",
            "31-Aug-2023 |     Poparide     | Ida ao St. Thomas Campus      |    Transporte            | $ 24.20",
            "31-Aug-2023 |     Poparide     | Volta do St. Thomas Campus    |    Transporte            | $ 19.20",
            "02-Sep-2023 |     Açaí Concept | Encontro BR (intake Set/2023) |    Alimentação           | $ 23.80",
            "02-Sep-2023 |     TimHortons   | Café                          |    Alimentação           | $ 1.56",
            "03-Sep-2023 |     Costco       | Carrinho Costco               |    Alimentação           | $ 124.29",
            "03-Sep-2023 |     Value Mart   | Folhas + Guaraná + Calabresa  |    Alimentação           | $ 25.25",
            "03-Sep-2023 |     Costco       | Pepsi + Sorvetes              |    Alimentação           | $ 7.54",
            "03-Sep-2023 |     Costco       | Feira                         |    Alimentação           | $ 172.14",
            "05-Sep-2023 |     Amazon       | Garrafinha                    |    Alimentação           | $ 41.14",
            "07-Sep-2023 |     NoFrios      | Cereal + Suco + Berries       |    Alimentação           | $ 16.46",
            "07-Sep-2023 |     McDonalds    | Sorvete                       |    Alimentação           | $ 2.10",
            "07-Sep-2023 |     Walmart      | Carrinho de compra            |    Utensílios domésticos | $ 49.92",
            "07-Sep-2023 |     Walmart      | Banana + Gillete + SabãoPrato |    Alimentação           | $ 48.56"
        );
    @Test
    @Disabled
    public void loadBills() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    STRINGS.stream()
        .map(
            s -> {
              Bill b = new Bill();
              String[] line = s.split("\\|");

              try {
                b.setDocumentDate(
                        Date.from(LocalDate.parse(line[0].trim(), formatter)
                                .atStartOfDay()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()));
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
              b.setVendor(new Vendor(null, line[1].trim(), null));
              b.setDescription(line[2].trim());
              b.setTags(List.of(new Tag(null, line[3].trim())));
              b.setTotal(Float.parseFloat(line[4]
                      .replace("$ ", "")
                      .replace(",", "").trim()));
              b.setCreatedDate(Date.from(Instant.now()));
              return b;
            })
            .forEach(this::createBillWithPost);
            //.forEach(System.out::println);
    }

    public void createBillWithPost(Bill bill) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String billJson = objectMapper.writeValueAsString(bill);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/bills"))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(billJson))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());
            
            int statusCode = response.statusCode();
            System.out.println("Response Status Code: " + statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
