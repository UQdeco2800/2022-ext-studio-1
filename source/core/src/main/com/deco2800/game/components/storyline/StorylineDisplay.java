package com.deco2800.game.components.storyline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorylineDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(StorylineDisplay.class);
  private final GdxGame game;

  private Table rootTable;

  public StorylineDisplay(GdxGame game) {
    super();
    this.game = game;
  }

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {

    Table table = makeSettingsTable();
    Label title = new Label("storyline", skin, "title");
    title.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2f,Gdx.graphics.getHeight()-title.getHeight()-15);


    Image inventoryBG =
            new Image(
                    ServiceLocator.getResourceService()
                            .getAsset("images/storyline_bg.jpg", Texture.class));
    inventoryBG.setWidth(Gdx.graphics.getWidth());
    inventoryBG.setHeight(Gdx.graphics.getHeight());

    stage.addActor(inventoryBG);
    stage.addActor(title);
    stage.addActor(table);

    Table exitTabkle = makeMenuBtns();
    stage.addActor(exitTabkle);

  }

  private Table makeSettingsTable() {

    UserSettings.Settings settings = UserSettings.get();
    int width = Gdx.graphics.getWidth();

    Table mainTable = new Table();




    mainTable.setFillParent(true);
    mainTable.setWidth(500);
    Table table = new Table();
    TextButton oneBt = new TextButton("storyline..1", skin);
    TextButton twoBt = new TextButton("storyline..2", skin);
    TextButton threeBt = new TextButton("storyline..3", skin);
    TextButton fourBt = new TextButton("storyline..4", skin);
    TextButton fiveBt = new TextButton("storyline..5", skin);
    TextButton sixBt = new TextButton("storyline..6", skin);

    table.row();
    table.add(oneBt).padTop(30f).left();
    table.row();

    table.add(twoBt).padTop(15f).left();
    table.row();
    table.add(threeBt).padTop(15f).left();
    table.row();
    table.add(fourBt).padTop(15f).left();
    table.row();
    table.add(fiveBt).padTop(15f).left();
    table.row();
    table.add(sixBt).padTop(15f).left();
    table.row();
    mainTable.add(table).width(300);
    Label label = new Label("", skin, "title");
    label.setWrap(true);
    label.setFontScale(0.5f);
    label.setPosition(0, 0);

    oneBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText(
                        "Player woke up in the sound of calling. They knew it was from Zoe, the researcher, yet they were unsure why she was so fretful. The noises made by various devices reminded them that they were in the laboratory, other than that, they could see nothing but plants in the lab faintly illuminating.\n" +
                        "\n" +
                        "(restore power to continue, task by researcher)\n" +
                        "\n" +
                        "As soon as the power was back on, they saw someone laying weltering in blood. (find clues from the body, task from Zoe) It was Nereus, the princess of Atlantis, who was the guardian of this land. Her death meant the fall of Atlantis. After scanning the body, player found out that she seemed to hobble here with all her strength [conclude from bloodstain]. It was an assassination. The wound on her waist opened up more while she was on her way to the lab, slowly taking away her life. Her scales used to be glittering with a riot of colors, but now it was shattered and lifeless. (get one shattered scale)\n" +
                        "\n" +
                        "(discuss with researcher to continue)\n" +
                        "\n" +
                        "Zoe calmed down immediately to analyze the situation. From the dialogue, player got to know that the scales still remained part of Nereus’ memories which would fade away in three days. Player had to collect the scales to solve the case while trying to save the sunk Atlantis (general tasks, not from npcs).");
              }
            });
    twoBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("In order to find the murderer, the player had to get out of the lab. (find the wetsuit to continue) As they were on their way, a woman seemed to be attracted by them. She rushed to the player, staring at the gloomy scale held by them as if she was crazy. Never had the player seen such a terrified expression on anyone's face. It was like a mix of shock, pain and disgust. She asked the player if the princess was dead. In the gaze of startle, the woman told them that Nereus was murdered by a man who was close to her. Neither did the princess suspected him, nor saw the assassination coming. Everything went normal until the man hugged the princess. The woman seemed to have the ability to read the memories on the scales. Yet since the scale was too broken to store more information, the woman could only hear the voice and see the body shape of the murderer. Before the player left, they got to know that the woman was called Metis. (player can get clues from her after collecting the scales)\n" +
                        "\n" +
                        "Fortunately Atlantis was quite small. The player went to the village and saw Doris was sitting in her garden, looking upset. A scale was held by her. Even if the scale's original owner had died, it was still trying to shine in the hazy underwater sunlight. (Talk to Doris to continue)\n" +
                        "\n" +
                        "Doris was blaming herself for not keeping the scale well enough. The scale was a gift from the princess a couple years ago. Nereus believed that her scale could protect the ones she loved even when she was not around them. Doris carefully treasured and wore that as a necklace until yesterday. When she was looking at the mirror, part of the scale was turning grey because of the death of the princess. Of course, she wasn't aware of that. She heard some ways to brighten the jewelry so she wanted to give it a try. Please find some Fluorescent corals for Doris. (Find some Fluorescent corals and give it to Doris to continue, task by Doris)");
              }
            });
    threeBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("*if the player chose to get back to Metis, she would tell the player that the scale was too damaged to be read. They should head out to restore it.\n" +
                        "\n" +
                        "The people arguing on the street caught player’s attention. As they walked through the crowd, they saw one man screaming at another person, with eyes blazing with fury. Heph, the bad-tempered restorer, was known for his superior skills and his weird personality. A small damage of his work could trigger a fight with someone random on the street. There were rumors saying he was good at repairing only because he had to learn restoring the things he broken into pieces. (go check the situation to continue)\n" +
                        "\n" +
                        "Maybe the Heph can help restore the scale, the player thought. They managed to stop the argument and dragged him aside. In order to persuade this stubborn man, they told him part of the story, except the death of Nereus. A lie needs more lies to cover. It was not long before Heph detected the lie. Apparently, he was furious. In spite of unknowing the whole truth, he still agreed to help them for the sake of the scale. But the prices for that were more things to collect. 'It's a fair exchange.' Heph sneered. (collect seaweed, Fluorescent corals, jellyfish, task by Heph)\n" +
                        "\n" +
                        "*i was just randomly listing things. you can add or delete as you want.\n" +
                        "Heph gave back the repaired scale, mumbling the people's carelessness. Orpheus hurried to him last week, begging him to save his irises, whose pedals were like being ripped off by someone. The poor musical actor was about to give the irises to the princess but somehow the plant was dead before he sent his feelings to her. Player decided to go to the theater to meet this man.");
              }
            });
    fourBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("(Having gotten the thearter ticket)They successfully came to the theater,finding Orpheus crooning a sorrow melody.The gorgeous clothes couldn't hide the gloom in his eyes.Looking down with his eyes,a few irises scattered on the ground.(They can choose to tell him the truth that Nereus dead or not.)After struggling for a short while,players determined to inform him of the death of Orpheus and attempted to get a scale from him.\n" +
                        "\n" +
                        "He stopped singing and a big shock appeared on his face.Orpheus couldn't accept it,even his love for her had never been shown at any time before.After spending a long time to calm down,he \"It felt meant to be...\",muttered Orpheus.He clenched the scale in the palm of his hand and seemed unwilling to hand it over to anyone. So, players must come up with a good idea to get the scale.\n" +
                        "\n" +
                        "(An option can appear that try asking him for scales)\n" +
                        "\n" +
                        "However,great sorrow and anger kept him from quelling for a long time,he failed in considering other things just refused them directly for the scale if the last reminder of memory on her.In order to collect all scales,they propose cooperation to find the murderer(or traitor)together.Hearing their intensions, he was a little shaken, but didn't want to accept the kindness of these strangers so easily.Remind that Nereus was kind-hearted and treated everyone very well, also not having any grudge against someone.It looked hard to know who the murderer was, but there's a good chance that it's someone close to her.Thus, he delegate a task to search for some information (tips)about Neuers's family to players.\n" +
                        "\n" +
                        "*the task can be some simple reasoning games or else.the tips can be the introduction of her family members.Once finished the task, players need to come back to meet Orpheus again to get the scale.");
              }
            });
    fiveBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("Player collected information about Nereus' family.Her father was king of Atlantis but stepped down from the throne a few years ago.Her mom was also in charge of the main regime in Atlantis and had high admiration among villagers .She loved Nereus for her talent of leadership. However Ares has strong skills in swordsmanship though, wasn't good enough in decisive aspects especially to be the guardian of Atlantis.Player went back to Orpheus. They combed through together. Orpheus mentioned that he saw Heph shouting at troublemakers on the death of the princess was none of his business on his way to the theatre.But those who love the princess asserted that he was the murderer since he always showed violence to anyone having some small conflicts with him. And Heph didn't agree with the political philosophy of the princess and he thought  Ares was more suitable to guard Atlantis for having superb power and swordsmanship he admired for. Once he was heading for theatre from the palace, he heard Nereus' parents were quarreling and Neures were sobbing softly.He felt very sorrow but pretended not hearing it and walked away. He didn't want to reveal any love for the princess yet considering he was wanting at that time.They decided to go back to the lab to ask for Zoe’s help. Analyzing for a while, he proposed to put together all the scales and look for Metis to read the princess' memory attached to it.They got outside of the lab and fund Metis in the village.Metis touched the assembled scales and said \"I saw him...\"in a tone of fear and disbelief.");
              }
            });
    sixBt.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                label.setText("No one knew why Heph held the knife, so they determined to find Ares to  ask why he did it. A man emerged from the corner.It was Ares. He heard all the conversations. Facing everyone's doubts, his conscience felt bad and finally admitted his crime. As for Heph, he also admitted he bought Heph. Ares heard from his subordinates that because of Heph's bad temper and violence, he accidentally smashed the gem that the nobles sent to him for restoration in a conflict with others. He was very flustered for knowing the damage was beyond repair but he couldn't afford it. If discovered by nobles, the result would be unimaginable.Thus, Ares came to meet Heph and promised to keep it secret and hush people up. The premise is ,help him kill Nereus. Heph was very hesitant, he never wanted to kill people no matter how violent he was. However, if he didn't agree, not only will the nobles demand huge compensation, but people will be afraid to send things to him to be repaired, because they may be permanently broken due to his violent temper anytime and anywhere. Without any other choice, he made this exchange. At the same time, Ares let his subordinates re-engraved an identical gem and return it to the nobles. He was not a professional killer. It hurt him after killing Nereus. He felt sorry for everyone  including  his sister who was growing up together with him and very loved him.He knew what awaited him next. Considering what he had done, he decided to unsink Atlantis to bring light to the whole of Atlantis City. He went back to the lab with everyone and pressed a switch in the corner that was hidden by a painting covered in scales. Finally, Atlantis was rising slowly from the bottom of the sea.The whole village ushered in the dawn of the long-lost past.");
              }
            });

    mainTable.add(label).top().padTop(30).width(width/2);



    return mainTable;
  }


  private void exitMenu() {
    game.setScreen(ScreenType.MAIN_MENU);
  }

  private Integer parseOrNull(String num) {
    try {
      return Integer.parseInt(num, 10);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  protected void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  private Table makeMenuBtns() {
    TextButton exitBtn = new TextButton("Exit", skin);

    exitBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Exit button clicked");
                exitMenu();
              }
            });

    Table table = new Table();
    table.add(exitBtn).expandX().left().pad(0f, 100f, 100f, 0f);
    return table;
  }

  @Override
  public void update() {
    stage.act(ServiceLocator.getTimeSource().getDeltaTime());
  }

  @Override
  public void dispose() {
    rootTable.clear();
    super.dispose();
  }
}
