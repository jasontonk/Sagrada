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
		rules.setText("SPELONTWERP\r\n" + 
				"Daryl Andrews (@darylmandrews)\r\n" + 
				"Adrian Adamescu (@Aadrian131)\r\n" + 
				"ONTWIKKELING\r\n" + 
				"Ben Harkins (@BenHarkins)\r\n" + 
				"GRAPHISCH ONTWERP & ILLUSTRATIES\r\n" + 
				"Peter Wocken Design LLC (@PeterWocken)\r\n" + 
				"UITGEGEVEN DOOR\r\n" + 
				"Floodgate Games (@FloodgateGames)\r\n" + 
				"Spelers : 1-4\r\n" + 
				"Leeftijd : 14 +\r\n" + 
				"Tijd : 40 min\r\n" + 
				"Églomisé Borstel\r\n" + 
				"2\r\n" + 
				"Verplaats een dobbelsteen in je\r\n" + 
				"raam. Je mag de voorwaarden\r\n" + 
				"voor kleur negeren\r\n" + 
				"Je moet alle andere voorwaarden\r\n" + 
				"nog steeds respecteren\r\n" + 
				"12 Gereedschapskaarten\r\n" + 
				"12 Patroonkaarten\r\n" + 
				"Aurora Sagradis\r\n" + 
				"Luz Celestial\r\n" + 
				"Fractal Drops\r\n" + 
				"4 Glasramen\r\n" + 
				"als Spelerborden\r\n" + 
				"Rondespoor (voorzijde)\r\n" + 
				"Scorespoor (achterzijde)\r\n" + 
				"Lichte Tinten\r\n" + 
				"Sets van waardes 1 & 2\r\n" + 
				"2\r\n" + 
				"10 Gedeelde\r\n" + 
				"Doelkaarten\r\n" + 
				"# Tinten Groen • Persoonlijk\r\n" + 
				"Som van waardes op groene dobbelstenen\r\n" + 
				"5 Persoonlijke\r\n" + 
				"Doelkaarten\r\n" + 
				"90 Dobbelstenen\r\n" + 
				"(18x5 Kleuren - rood, geel,\r\n" + 
				"groen, blauw, paars)\r\n" + 
				"STOP!\r\n" + 
				"We leren je het spel! Bezoek\r\n" + 
				"FloodgateGames.com/Sagrada\r\n" + 
				"en bekijk de video-uitleg.\r\n" + 
				"24 Betaalstenen\r\n" + 
				"4 Scorefiches\r\n" + 
				"Stoffen Zak\r\n" + 
				"Voorbereiding voor de Spelers\r\n" + 
				"• Schud de Persoonlijke Doelkaarten (met een grijze\r\n" + 
				"dobbelsteen op de rug) en geef er gedekt 1 aan elke\r\n" + 
				"speler. De spelers mogen in het geheim naar hun\r\n" + 
				"kaart kijken.\r\n" + 
				"• Geef elke speler 2 willekeurige Patroonkaarten\r\n" + 
				"en 1 Glasraam als Spelerbord. Elke speler kiest 1\r\n" + 
				"van de 4 Patroonkaarten om mee te spelen (voor- of\r\n" + 
				"achterzijde). De andere kaart wordt niet gebruikt.\r\n" + 
				"Opmerking : de moeilijkheidsgraad van de\r\n" + 
				"Patroonkaarten varieert van niveau 3 (makkelijk) tot\r\n" + 
				"niveau 6 (moeilijk) en is aangeduid met stipjes naast\r\n" + 
				"de naam. Voor moeilijke Patroonkaarten ontvang je\r\n" + 
				"meer Betaalstenen.\r\n" + 
				"• Geef de spelers een aantal Betaalstenen op basis\r\n" + 
				"van de moeilijkheidsgraad van hun gebruikte kaart.\r\n" + 
				"• Schuif de gekozen Patroonkaart in de gleuf onderaan\r\n" + 
				"je Spelerbord.\r\n" + 
				"• Leg het Scorefiche van jouw kleur naast het\r\n" + 
				"Rondespoor. Je gebruikt het pas bij de puntentelling\r\n" + 
				"aan het einde van het spel.\r\n" + 
				"Voorbereiding van het Spel\r\n" + 
				"• Leg het Rondespoor in het midden van de tafel.\r\n" + 
				"• Schud alle Gereedschapskaarten en leg er 3\r\n" + 
				"open in het midden van de tafel.\r\n" + 
				"• Schud de Gedeelde Doelkaarten (met een blauwe\r\n" + 
				"dobbelsteen op de rug) en leg er 3 open.\r\n" + 
				"• Stop alle 90 dobbelstenen in de stoffen zak.\r\n" + 
				"• De speler die als laatste een kathedraal bezocht, is\r\n" + 
				"de Startspeler en neemt de stoffen zak.\r\n" + 
				"Stop alle overgebleven Doel-, Gereedschaps- en\r\n" + 
				"Patroonkaarten weer in de doos. Deze doen niet mee.\r\n" + 
				"Opmerking: de voorbereiding is hetzelfde voor 2, 3, of\r\n" + 
				"4 spelers.\r\n" + 
				"Églomisé Borstel\r\n" + 
				"2\r\n" + 
				"Verplaats een dobbelsteen in je\r\n" + 
				"raam. Je mag de voorwaarden\r\n" + 
				"voor kleur negeren\r\n" + 
				"Je moet alle andere voorwaarden\r\n" + 
				"nog steeds respecteren\r\n" + 
				"Kleurvariëteit per Kolom\r\n" + 
				"Kolommen zonder herhaalde 5 kleuren\r\n" + 
				"Lichte Tinten 2 Sets van waardes 1 & 2\r\n" + 
				"Driepuntstang\r\n" + 
				"1\r\n" + 
				"Nadat je een dobbelsteen kiest,\r\n" + 
				"mag je de waarde ervan met 1\r\n" + 
				"verhogen of verlagen\r\n" + 
				"1 mag geen 6 worden, 6 geen 1\r\n" + 
				"Loodopenhaler\r\n" + 
				"4\r\n" + 
				"Verplaats exact 2 dobbelstenen\r\n" + 
				"Je moet hierbij alle voorwaarden\r\n" + 
				"voor de plaatsing respecteren\r\n" + 
				"Luz Celestial\r\n" + 
				"2\r\n" + 
				"3\r\n" + 
				"1\r\n" + 
				"3\r\n" + 
				"4\r\n" + 
				"5\r\n" + 
				"2\r\n" + 
				"1\r\n" + 
				"Kleurvariëteit 4 Sets van één van elke kleur\r\n" + 
				"4\r\n" + 
				"A\r\n" + 
				"A\r\n" + 
				"B\r\n" + 
				"C\r\n" + 
				"D\r\n" + 
				"E\r\n" + 
				"D\r\n" + 
				"B\r\n" + 
				"C\r\n" + 
				"E\r\n" + 
				"5\r\n" + 
				"Overwinningspunten (OP)\r\n" + 
				"voor elke volledige set van dit\r\n" + 
				"type\r\n" + 
				"Opmerking: dit symbool wordt enkel gebruikt bij het spel voor 1 speler\r\n" + 
				"Spelverloop\r\n" + 
				"Een spelletje Sagrada verloopt over 10 rondes.\r\n" + 
				"De Startspeler trekt en werpt elke ronde willekeurig\r\n" + 
				"dobbelstenen uit de zak. Het aantal getrokken\r\n" + 
				"dobbelstenen is afhankelijk van het aantal spelers:\r\n" + 
				"2 Spelers - 5 Dobbelstenen\r\n" + 
				"3 Spelers - 7 Dobbelstenen\r\n" + 
				"4 Spelers - 9 Dobbelstenen\r\n" + 
				"Dus: 2 per speler, plus 1 extra dobbelsteen\r\n" + 
				"Zodra ze geworpen zijn, vormen deze dobbelstenen\r\n" + 
				"het Aanbod.\r\n" + 
				"Te beginnen met de Startspeler en daarna in wijzerzin,\r\n" + 
				"neemt elke speler een beurt. Tijdens je beurt mag\r\n" + 
				"je elk van de volgende acties in om het even welke\r\n" + 
				"volgorde uitvoeren:\r\n" + 
				"• Kies 1 dobbelsteen uit het Aanbod en leg hem\r\n" + 
				"in een beschikbaar vak op je Glasraam\r\n" + 
				"• Gebruik 1 Gereedschapskaart door\r\n" + 
				"Betaalstenen in te leveren\r\n" + 
				"De acties zijn optioneel - Je mag ervoor kiezen om één of\r\n" + 
				"beide acties uit te voeren, of om te passen en geen acties\r\n" + 
				"uit te voeren.\r\n" + 
				"De beurten volgen elkaar in wijzerzin op, waarbij elke\r\n" + 
				"speler een beurt neemt of past. Voorbeeld - Emily is\r\n" + 
				"aan de beurt (1), daarna Ben (2) en dan Marie (3).\r\n" + 
				"Zodra de laatste speler zijn eerste beurt heeft\r\n" + 
				"uitgevoerd, gaat de ronde verder in tegenwijzerzin.\r\n" + 
				"Te beginnen met de laatste speler voert iedereen\r\n" + 
				"een tweede actie uit (een tweede steen kiezen uit het\r\n" + 
				"Aanbod, enz.) Voorbeeld - Marie voert haar tweede\r\n" + 
				"actie uit (4), daarna Ben (5), en dan Emily (6).\r\n" + 
				"Zodra de Startspeler zijn tweede actie heeft\r\n" + 
				"uitgevoerd, volgt het Einde van de Ronde.\r\n" + 
				"Dobbelstenen Plaatsen\r\n" + 
				"Bij de plaatsing van dobbelstenen op het Glasraam geldt:\r\n" + 
				"• Eerste dobbelsteen: de eerste dobbelsteen van het spel moet je\r\n" + 
				"in een vak aan de rand of in een van de hoeken leggen\r\n" + 
				"• Daarna moet elke volgende dobbelsteen grenzen aan een\r\n" + 
				"eerder gelegde steen, ofwel diagonaal (andere rij en kolom)\r\n" + 
				"ofwel orthogonaal (zelfde rij of kolom)\r\n" + 
				"• De dobbelsteen moet voldoen aan de voorwaarden van\r\n" + 
				"kleuren of waardes. Aan witte vakken zijn geen voorwaarden\r\n" + 
				"verbonden. Voorbeeld - een rood vak op je glasraam mag je\r\n" + 
				"enkel vullen met een rode dobbelsteen (de waarde maakt niet\r\n" + 
				"uit). Een vak met een 3 mag je enkel vullen met een dobbelsteen\r\n" + 
				"met waarde 3 (de kleur maakt niet uit)\r\n" + 
				"• Nieuwe dobbelstenen mogen nooit horizontaal of verticaal\r\n" + 
				"grenzen aan dobbelstenen met dezelfde kleur of dezelfde\r\n" + 
				"waarde. Voorbeeld - twee rode dobbelstenen of twee stenen\r\n" + 
				"met waarde 3 mogen niet naast elkaar liggen\r\n" + 
				"Je mag ervoor kiezen om tijdens je beurt geen dobbelsteen te\r\n" + 
				"nemen.\r\n" + 
				"Opmerking: op een wit vak mag je om het even welke\r\n" + 
				"dobbelsteen plaatsen, zolang die aan alle bovenstaande voorwaarden voldoet\r\n" + 
				"Tip: probeer geen dobbelstenen te leggen naast een vak dat ermee\r\n" + 
				"overeenkomt in cijfer of kleur (bijvoorbeeld een 2 naast een vak\r\n" + 
				"dat een 2 vereist of een rode steen naast een rood vak).\r\n" + 
				"Gereedschapskaarten & Betaalstenen\r\n" + 
				"Je mag tijdens je beurt Betaalstenen spenderen om\r\n" + 
				"een speciale eigenschap van 1 Gereedschapskaart te\r\n" + 
				"activeren:\r\n" + 
				"1. Leg Betaalstenen op de Gereedschapskaart - 1 als\r\n" + 
				"er nog geen stenen op de kaart liggen, en 2 als dat\r\n" + 
				"wel het geval is.\r\n" + 
				"2. Gebruik de eigenschap van de Gereedschapskaart.\r\n" + 
				"Je bent niet verplicht om tijdens je beurt een\r\n" + 
				"Gereedschapskaart te activeren.\r\n" + 
				"Tip: hoe vroeger je een Gereedschapskaart\r\n" + 
				"activeert, hoe minder Betaalstenen je daarvoor\r\n" + 
				"nodig hebt. De Gereedschapskaarten worden\r\n" + 
				"echter veel nuttiger naarmate er meer dobbelstenen\r\n" + 
				"op de Glasramen van de spelers liggen. Fractal Drops\r\n" + 
				"Driepuntstang\r\n" + 
				"1\r\n" + 
				"Nadat je een dobbelsteen kiest,\r\n" + 
				"mag je de waarde ervan met 1\r\n" + 
				"verhogen of verlagen\r\n" + 
				"1 mag geen 6 worden, 6 geen 1\r\n" + 
				"Voorbeeld: op deze 3 vakken\r\n" + 
				"voldoet de rode 2 aan\r\n" + 
				"alle voorwaarden van de\r\n" + 
				"plaatsing.\r\n" + 
				"1\r\n" + 
				"Emily\r\n" + 
				"(Startspeler)\r\n" + 
				"Ben\r\n" + 
				"Marie\r\n" + 
				"2\r\n" + 
				"3 4\r\n" + 
				"5\r\n" + 
				"6\r\n" + 
				"Fractal Drops\r\n" + 
				"Einde van de Ronde\r\n" + 
				"Leg de resterende dobbelsteen op het Rondespoor\r\n" + 
				"en bedek hiermee het cijfer van de zopas gespeelde\r\n" + 
				"ronde. Als er meerdere dobbelstenen overblijven\r\n" + 
				"(doordat sommige spelers in hun beurt geen\r\n" + 
				"dobbelsteen wegnamen), leg deze dan allemaal bij\r\n" + 
				"het vak van de zopas gespeelde ronde.\r\n" + 
				"Geef de stoffen zak in wijzerzin door aan de volgende\r\n" + 
				"speler, die de Startspeler is voor de volgende ronde.\r\n" + 
				"Voorbeeld - Ben is nu de startspeler voor de tweede\r\n" + 
				"ronde.\r\n" + 
				"Als dit het einde was van de 10de ronde, dan eindigt\r\n" + 
				"het spel en worden de eindscores berekend.\r\n" + 
				"Berekening van de Eindscore\r\n" + 
				"Na de 10de ronde is het spel voorbij.\r\n" + 
				"1. Haal de dobbelstenen van het Rondespoor en draai\r\n" + 
				"het om naar de zijde met het Scorespoor.\r\n" + 
				"2. De spelers gebruiken hun Scorefiche om hun positie\r\n" + 
				"aan te duiden op het Scorespoor. Als je meer dan 50\r\n" + 
				"punten scoort, draai dan je fiche om.\r\n" + 
				"De spelers ontvangen Overwinningspunten voor het\r\n" + 
				"volgende:\r\n" + 
				"• Alle Gedeelde Doelkaarten - de spelers kunnen voor\r\n" + 
				"elke kaart meerdere malen punten ontvangen. Doelen\r\n" + 
				"die te maken hebben met rijen of kolommen leveren\r\n" + 
				"enkel punten op voor volledige rijen of kolommen\r\n" + 
				"(zonder lege vakken).\r\n" + 
				"• Hun Persoonlijke Doelkaart - de waardes van de\r\n" + 
				"dobbelstenen in de toegewezen kleur.\r\n" + 
				"• Betaalstenen - 1 Overwinningspunt per ongebruikte\r\n" + 
				"steen.\r\n" + 
				"• De spelers verliezen 1 punt per leeg vak op hun\r\n" + 
				"glasraam.\r\n" + 
				"De speler met de meeste Overwinningspunten is\r\n" + 
				"de winnaar. Is er een gelijkspel, dan vergelijken de\r\n" + 
				"betrokken spelers in volgorde: de meeste punten van hun\r\n" + 
				"Persoonlijke Doelen, de meeste resterende Betaalstenen.\r\n" + 
				"Is er dan nog een gelijkspel, dan wint de speler die in de\r\n" + 
				"laatste ronde het verst van de Startspeler zat.\r\n" + 
				"Scorevoorbeeld\r\n" + 
				"Dit voorbeeld gebruikt de Gedeelde\r\n" + 
				"Doelen van de Voorbereiding van het\r\n" + 
				"Spel, en het glasraam hiernaast.\r\n" + 
				"Gedeelde Doelen\r\n" + 
				"Kleurvariëteit per Kolom : 5 OP\r\n" + 
				"voor kolommen 4 en 5 = 10 OP. 0\r\n" + 
				"Punten voor kolommen 1 en 3, omdat\r\n" + 
				"deze onvolledig zijn,en 0 punten\r\n" + 
				"voor kolom 2 omdat er 2 groene\r\n" + 
				"dobbelstenen in liggen.\r\n" + 
				"Lichte Tinten : 2 OP per set van dobbelstenen met waarde 1 en\r\n" + 
				"2 (1’en op B2, B4, 2’en op A3, D4 , B5). 4 OP voor twee sets. De\r\n" + 
				"derde dobbelsteen met waarde 2 maakt geen deel uit van een set\r\n" + 
				"en scoort geen punten.\r\n" + 
				"Kleurvariëteit : 3 sets van 5 verschillende kleuren voor 4 OP elk\r\n" + 
				"= 12 OP.\r\n" + 
				"Tip : het aantal Sets van een type is altijd gelijk aan de kleinste\r\n" + 
				"hoeveelheid dobbelstenen die deel uitmaken van dat type.\r\n" + 
				"Persoonlijke Doelen : om Tinten Paars te scoren, tel je de\r\n" + 
				"waardes van je paarse dobbelstenen bij elkaar op (C1, C4 en A5),\r\n" + 
				"voor 5 + 6 + 6 = 17 OP.\r\n" + 
				"Betaalstenen : 1 OP per resterende steen, in dit geval 0 OP.\r\n" + 
				"Vrije vakken: -1 OP voor elk leeg vak (A1, B3 en C3) = -3 OP.\r\n" + 
				"Totaal: 10 OP + 4 OP + 12 OP + 17 OP + 0 OP -3 OP = 40 OP!\r\n" + 
				"Vergissingen bij de Plaatsing\r\n" + 
				"Als je een speler (of jezelf) betrapt op een fout tegen de voorwaarden van\r\n" + 
				"de plaatsing, dan moet deze speler onmiddellijk dobbelstenen naar keuze\r\n" + 
				"wegnemen uit zijn of haar Glasraam totdat er weer aan alle voorwaarden\r\n" + 
				"is voldaan. Deze dobbelstenen gaan uit het spel en de lege vakken die\r\n" + 
				"hierdoor ontstaan, tellen gewoon als minpunten bij de eindscore.\r\n" + 
				"Spel voor 1 Speler\r\n" + 
				"Als je Sagrada in je eentje speelt, dan probeer je een Doelscore te\r\n" + 
				"verslaan. De Doelscore is de som van de waardes van alle dobbelstenen\r\n" + 
				"op het Rondespoor aan het einde van het spel.\r\n" + 
				"Voorbereiding\r\n" + 
				"Gebruik de normale voorbereiding, met een aantal uitzonderingen:\r\n" + 
				"• Haal de Betaalstenen uit het spel.\r\n" + 
				"• Leg 2 Gedeelde Doelen en 2 Persoonlijke Doelen open op de tafel.\r\n" + 
				"• Gebruik een aantal Gereedschapskaarten, afhankelijk van\r\n" + 
				"de moeilijkheidsgraad die je wilt proberen - van 1 kaart (Zeer\r\n" + 
				"Moeilijk) tot 5 kaarten (Makkelijk).\r\n" + 
				"Spelverloop\r\n" + 
				"Het spel verloopt nog steeds over 10 rondes, met de volgende uitzonderingen:\r\n" + 
				"• Trek elke ronde 4 dobbelstenen uit de zak en werp ze.\r\n" + 
				"• Je krijgt 2 beurten om dobbelstenen te kiezen en/of een\r\n" + 
				"Gereedschapskaart te activeren, of om te passen.\r\n" + 
				"Gereedschapskaarten\r\n" + 
				"Je kunt elke Gereedschapskaart slechts eenmalig\r\n" + 
				"gebruiken door een dobbelsteen uit het Aanbod in\r\n" + 
				"te zetten. Om een Gereedschapskaart te activeren,\r\n" + 
				"leg je een dobbelsteen uit het Aanbod op de kaart die\r\n" + 
				"overeenstemt met de kleur links bovenaan de kaart.\r\n" + 
				"Opmerking: je mag elke Gereedschapskaart slechts één keer gebruiken.\r\n" + 
				"Verwijder zowel de ingezette dobbelsteen als de gebruikte kaart uit het spel.\r\n" + 
				"Einde van de Ronde\r\n" + 
				"Alle resterende dobbelstenen worden volgens de normale regels op\r\n" + 
				"het Rondespoor gelegd zonder hun waarde te veranderen. Als er geen\r\n" + 
				"dobbelstenen overblijven, gebruik dan ter vervanging een Scorefiche.\r\n" + 
				"Berekening van de Eindscore\r\n" + 
				"Na de 10de ronde is het spel voorbij. Bereken eerst de Doelscore van het\r\n" + 
				"Rondespoor voordat je het spoor omdraait.\r\n" + 
				"• De Doelscore is de som van de waardes van alle dobbelstenen op het\r\n" + 
				"Rondespoor. Dobbelstenen die je gebruikte voor Gereedschapskaarten\r\n" + 
				"tellen niet mee.\r\n" + 
				"• Bereken je score op de normale manier, maar je mag slechts één\r\n" + 
				"Persoonlijk Doel scoren, en elk leeg vak kost je 3 Overwinningspunten.\r\n" + 
				"• Je wint als je totale aantal Overwinningspunten groter is dan de ");
		
		rules.setMinSize(700, 400);
		rules.setEditable(false);
		choosePane.getChildren().addAll(textPane, yesno, rules);
		return choosePane;
	}

	
}
