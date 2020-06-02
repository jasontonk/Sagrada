package view;

import controller.AccountController;
import database.DataBaseConnection;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ChooseView extends Pane{

	private final int BUTTON_WIDTH = 100;
	private final int BUTTON_HEIGHT = 50;
	private Pane choosePane;
	private AccountController accountController;
	
	public ChooseView(AccountController accountController) {
		this.accountController = accountController;
	}
	
	public Pane makeChoosePane() {
		choosePane = new VBox();
		choosePane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

//		String url = "/images/loginbackground.jpg";
//		choosePane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource(url).toString()), null, null, null, null)));
		
		BorderPane textPane = new BorderPane();
		Text text = new Text("Heb je een account?");
		text.setStyle("-fx-font: 24 arial;");
		textPane.setCenter(text);
		
		Button yes = new Button("Ja");
		Button no = new Button("Nee");
		
		yes.setOnAction(e -> accountController.viewLogin());
		no.setOnAction(e -> accountController.viewRegister());
		
		
		
		HBox yesno = new HBox();
		yesno.getChildren().addAll(yes,no);
		yesno.setAlignment(Pos.CENTER);
		
		yes.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		no.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		TextArea rules = new TextArea();
		rules.setText("\r\n" + 
				"In de middeleeuwen waren kleurstoffen duur en alleen betaalbaar voor de kleine, rijke bovenlaag van de samenleving. "+"\n"
				+ "Het gevolg was dat er in het straatbeeld weinig kleur te bekennen was. "+"\n"
				+ "De meeste kleuren die je gezien zal hebben waren groen, bruin en grijstinten. "+"\n"
				+ "Maar voor God was natuurlijk alleen het beste goed genoeg. "+"\n"
				+ "En dus werden kosten nog moeite gespaard om de kerken en kathedralen de stralende middelpunten van de steden en dorpen te maken. "+"\n"
				+ "En dan mocht een kleurrijk glas en lood raam natuurlijk niet ontbreken. "+"\n"
				+ "We kijken nu nog steeds vol bewondering naar de prachtige ramen die in verschillende godshuizen te vinden zijn. "+"\n"
				+ "Kan je nagaan hoe indrukwekkend diezelfde kleurrijke ramen waren in de kleurloze tijd dat ze gemaakt werden!\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"In Sagrada kruipen de spelers in de huid van de fameuze glas in lood kunstenaars van de middeleeuwen. "+"\n"
				+ "Iedere speler probeert tijdens het spel het mooiste raam te produceren. "+"\n"
				+ "Alle spelers krijgen hiervoor een glas in lood raam (mooi stevig kartonnen frame) toegewezen en kiezen een ontwerp uit vier opties. "+"\n"
				+ "In dit raam moet een raster van 4 bij 5 vakjes nog worden ingevuld. "+"\n"
				+ "Verder worden aan het begin van het spel nog 3 kaarten neergelegd met daarop de elementen waarop de ramen aan het eind van het spel worden gewaardeerd"+"\n"
				+ " en 3 kaarten met speciale acties die spelers tegen betaling van glasdruppels kunnen uitvoeren. "+"\n"
				+ "Het aantal glasdruppels dat je krijgt, hangt af van hoe moeilijk het ontwerp is dat je hebt gekozen.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Het spel wordt in 10 rondes gespeeld. Iedere ronde worden er een aantal doorzichtige, gekleurde dobbelstenen uit een zakje getrokken. "+"\n"
				+ "De startspeler kiest een van deze dobbelstenen en legt hem in zijn frame. "+"\n"
				+ "De eerste dobbelsteen moet aan de rand liggen en de volgende dobbelstenen moeten horizontaal, verticaal of diagonaal aangrenzend worden gelegd. "+"\n"
				+ "Nadat alle spelers in een ronde hun eerste dobbelsteen hebben gepakt, pakken alle spelers in omgekeerde volgorde nog een dobbelsteen "+"\n"
				+ "(de laatste speler mag dus twee keer achter elkaar en de eerste speler pakt zowel de eerste als de laatste dobbelsteen).\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Bij het plaatsen van de dobbelstenen moet je rekening houden met het ontwerp van je raam. "+"\n"
				+ "Als er bijvoorbeeld een groen vakje in je ontwerp staat, dan moet je daar een groene dobbelsteen op leggen (ongeacht het aantal gegooide ogen). "+"\n"
				+ "En als er een 4 op een vakje staat dan moet daar dus een 4 komen te liggen (ongeacht de kleur). "+"\n"
				+ "Verder moet je er nog rekening mee houden dat dobbelstenen nooit horizontaal of verticaal grenzend naast een dobbelsteen met dezelfde kleur of waarde gelegd mogen worden.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Naast het plaatsen van de dobbelstenen mag je dus tegen betaling van glasdruppels de speciale acties uitvoeren. "+"\n"
				+ "Met deze acties mag je bijvoorbeeld dobbelstenen van plaats verwisselen, de waarde van een dobbelsteen veranderen of dobbelstenen opnieuw gooien. "+"\n"
				+ "De eerste speler die een actie kiest, betaalt hiervoor 1 glasdruppel. "+"\n"
				+ "De volgende speler betaalt al 2 glasdruppels en zo gaat de prijs voor iedere volgende keer dat een actie uitgevoerd wordt met 1 omhoog. "+"\n"
				+ "En dan te bedenken dat je maximaal 5 glasdruppels per spel hebt.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Na 10 rondes is het spel afgelopen en worden de ramen gewaardeerd. Aan het begin van het spel hebben alle spelers een kaart met een kleur gekregen. "+"\n"
				+ "Iedereen scoort net zo veel punten als er ogen op de dobbelstenen in deze kleur staan. Daarna worden de 3 score-kaarten afgehandeld. "+"\n"
				+ "Er zitten 10 verschillen score-kaarten in het spel dus elk spel is anders. "+"\n"
				+ "Op deze kaarten staat bijvoorbeeld dat je punten krijgt voor iedere kolom waar op elk vakje een andere kleur ligt. "+"\n"
				+ "Of je krijgt punten voor de kolommen waar ieder getal anders is. Of je krijgt punten voor elk setje van een dobbelsteen met een 1 en 2. "+"\n"
				+ "Vervolgens krijgen de spelers nog 1 punt voor elke glasdruppel die ze niet gebruikt hebben en 1 strafpunt voor elk leeg vakje dat ze in hun raam hebben zitten. "+"\n"
				+ "Wie daarna de meeste punten heeft, wint het spel.\r\n" + 
				"\r\n" + 
				"...en de waardering\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Sagrada is een lekker vlot puzzelspelletje, dat zowel bij families als bij verwende veelspelers in de smaak zal vallen. " +"\n"
				+ "De regels van Sagrada zijn simpel, maar je moet tijdens het spel nog goed opletten dat je niet per ongeluk een foutje maakt het plaatsen van een dobbelsteen. "+"\n"
				+ "Dit komt doordat je tegelijkertijd meerdere scoringsmogelijkheden aan het maximaliseren ben. "+"\n"
				+ "Je probeert vaak zowel een bepaalde combinatie van kleuren en getallen in de rijen en kolommen te krijgen en dan ook nog de dure dobbelstenen in jouw kleur te scoren. "+"\n"
				+ "En dan moet je ook nog rekening houden met de verplichte kleuren en getallen in jouw ontwerp. ");
		
		rules.setMinSize(700, 400);
		rules.setEditable(false);
		choosePane.getChildren().addAll(textPane, yesno, rules);
		return choosePane;
	}

	
}
