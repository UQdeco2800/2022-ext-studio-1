package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcEvictionMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(NpcEvictionMenuDisplay.class);
    private static final int CONFIRM_BUTTON_WIDTH = 100;
    private static final int CONFIRM_BUTTON_HEIGHT = 50;
    private static final int NPC_CARD_HEIGHT = 350;
    private static final  int NPC_CARD_WIDTH = 330;
    private static final int CONFIRM_BUTTON_LEFT_PADDING = 50;
    private static final int NPC_CARD_TOP_PADDING = 70;
    private static final int NPC_CARD_TOP_PADDING2 = 30;
    private static final int BACKGROUND_HEIGHT_GAP =40 ;
    private static final int BACKGROUND_WIDTH_GAP = 40;
    private static final int EXIT_BUTTON_SIZE_WIDTH = 100;
    private static final int EXIT_BUTTON_SIZE_HEIGHT = 100;
    private static final int EXIT_BUTTON_Y_POSITION = 1100;
    private static final int EXIT_BUTTON_X_POSITION = 2100;

    private final GdxGame game;
    private Table rootTable;
    private Table bgTable;


    public NpcEvictionMenuDisplay(GdxGame game) {
        super();
        this.game = game;

    }

    @Override
    public void create() {
        super.create();
        addActors();
    }
    Window window;
    Skin mskin;
    TextButton tbOk, tbCancel;
    Label confirmLabel;
    Label infoLabel;
    private void addActors() {

        Image backgroundNpcMenu =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/eviction_menu/evictionMenu_background.png", Texture.class));


        Table menuNpcs = makeNpcCards();
        /** build new style exit button */
        Button.ButtonStyle styleExit = new Button.ButtonStyle();
        styleExit.up = new TextureRegionDrawable(
                ServiceLocator.getResourceService()
                        .getAsset("images/eviction_menu/exitButton.png",Texture.class));
        //here is for button effect when you pressed on button
        styleExit.over = new TextureRegionDrawable(
                ServiceLocator.getResourceService()
                        .getAsset("images/eviction_menu/exitButton_selected.png",Texture.class));
        Button exitBtn = new Button(styleExit);

        exitBtn.setPosition((float) (stage.getWidth() * 0.928), (float) (stage.getHeight() * (1-0.1704)));
        exitBtn.setSize((float) (stage.getWidth()*0.035), (float) (stage.getHeight()*0.0593));
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitMenu();
                    }
                });
        bgTable =new Table();
        bgTable.setFillParent(true);
        bgTable.add(backgroundNpcMenu).height(Gdx.graphics.getHeight()-BACKGROUND_HEIGHT_GAP).width(Gdx.graphics.getWidth()-BACKGROUND_WIDTH_GAP);

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.add(menuNpcs).center();
        // for debugging ,it will show you red table line
       // rootTable.debug();

        stage.addActor(bgTable);
        stage.addActor(exitBtn);
        stage.addActor(rootTable);

    }

    private Table makeNpcCards() {


        /** build new style select button */
        Button.ButtonStyle styleSelect = new Button.ButtonStyle();
        styleSelect.up = new TextureRegionDrawable(
                ServiceLocator.getResourceService()
                        .getAsset("images/eviction_menu/selectButton_single.png",Texture.class));
        //here is for button effect when you pressed on button
        styleSelect.over = new TextureRegionDrawable(
                ServiceLocator.getResourceService()
                        .getAsset("images/eviction_menu/selectButton_selected.png",Texture.class));
        Button confirmBtn1 = new Button(styleSelect);
        Button confirmBtn2 = new Button(styleSelect);
        Button confirmBtn3 = new Button(styleSelect);
        Button confirmBtn4 = new Button(styleSelect);
        Button confirmBtn5 = new Button(styleSelect);
        Button confirmBtn6 = new Button(styleSelect);
        Button confirmBtn7 = new Button(styleSelect);
        Button confirmBtn8 = new Button(styleSelect);


        Drawable drawable = new TextureRegionDrawable(
                ServiceLocator.getResourceService()
                .getAsset("images/eviction_menu/evictionCard_single.png",Texture.class));

        ImageButton npcButton1 = new ImageButton(drawable);
        ImageButton npcButton2 = new ImageButton(drawable);
        ImageButton npcButton3 = new ImageButton(drawable);
        ImageButton npcButton4 = new ImageButton(drawable);
        ImageButton npcButton5 = new ImageButton(drawable);
        ImageButton npcButton6 = new ImageButton(drawable);
        ImageButton npcButton7 = new ImageButton(drawable);
        ImageButton npcButton8 = new ImageButton(drawable);
        Button[] confirmButtons = {confirmBtn1, confirmBtn2, confirmBtn3,
                confirmBtn4, confirmBtn5, confirmBtn6, confirmBtn7, confirmBtn8};
        for (int i=0;i<confirmButtons.length;i++){
            String index = String.valueOf(i);
            confirmButtons[i].addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
                            logger.debug("confirm button"+ index +" clicked");
                            dialog("confirm button" + index + " clicked");
                        }
                    });
        }


        npcButton1.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("npc  button clicked");
                        //entity.getEvents().trigger("start");
                    }
                });

        Table table = new Table();

        table.add(npcButton1).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING);
        table.add(npcButton2).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING);
        table.add(npcButton3).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING);
        table.add(npcButton4).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING);
        table.row();
        table.add(confirmBtn1).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn2).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn3).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn4).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.row();
        table.add(npcButton5).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING2);
        table.add(npcButton6).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING2);
        table.add(npcButton7).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING2);
        table.add(npcButton8).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING2);
        table.row();
        table.add(confirmBtn5).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn6).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn7).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn8).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);



        //table.debug();


        return table;
    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }



    private void dialog(String button_name) {
        TextureRegionDrawable wind = new TextureRegionDrawable(
                ServiceLocator.getResourceService().getAsset("images/eviction_menu/confirmBox.png", Texture.class));
        Window.WindowStyle win_style = new Window.WindowStyle(new BitmapFont(), Color.BLACK, wind);
        Window dialog = new Window("", win_style);  // background of dialog
        // set dialog
        float dialog_size_x = (float) (stage.getWidth() * 0.2537);
        float dialog_size_y = (float) (stage.getHeight() * 0.3037);
        dialog.setSize(dialog_size_x, dialog_size_y);
        float dialog_pos_x = (float) (stage.getWidth() * 0.3756);
        float dialog_pos_y = (float) (stage.getHeight() * (1-0.65));
        dialog.setPosition(dialog_pos_x, dialog_pos_y);
        // set effect of buttons
        Button.ButtonStyle yesStyle = new Button.ButtonStyle();
        yesStyle.up = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/eviction_menu/confirmBtn_ok.png",Texture.class));
        yesStyle.over = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/eviction_menu/confirmBtn_ok1.png",Texture.class));
        Button yesButton = new Button(yesStyle);

        Button.ButtonStyle cancelStyle = new Button.ButtonStyle();
        cancelStyle.up = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/eviction_menu/confirmBtn_cancel.png",Texture.class));
        cancelStyle.over = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/eviction_menu/confirmBtn_cancel1.png",Texture.class));
        Button cancelButton = new Button(cancelStyle);

        yesButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("ok_button from " + button_name + " clicked");
                        // Actions
                        dialog.remove();
                    }
                });

        cancelButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("cancel_button from " + button_name + " clicked");
                        dialog.remove();
                    }
                });
        // adding buttons into dialog
        cancelButton.setSize((float) (dialog_size_x * 0.361), (float) (dialog_size_y * 0.2317));
        cancelButton.setPosition((float) (dialog.getWidth()*0.1067), 0);
        yesButton.setSize((float) (dialog_size_x * 0.377), (float) (dialog_size_y * 0.2317));
        yesButton.setPosition((float) (dialog.getWidth()*0.5239), 0);
        dialog.addActor(cancelButton); dialog.addActor(yesButton);
        stage.addActor(dialog);
    }
}


