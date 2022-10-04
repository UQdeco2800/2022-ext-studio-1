package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class LoadGameDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameDisplay.class);
    private final GdxGame game;

    private Table table;

    private Table descriptionTable;
    private Table rootTable;

    private Table popupBGTable;

    private Table confirmPopup;

    private boolean popupIsOpen = false;

    public LoadGameDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);


        Image title =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu/LoadGameTitle.png", Texture.class));
        title.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2f,Gdx.graphics.getHeight()-title.getHeight()-15);

        stage.addActor(title);


        stage.addActor(table);

        table.add(makeLoadSlots());

        Table loadTitle = new Table();
        loadTitle.setFillParent(true);
        Label loadSubTitle = new Label("Load From Your Saved Games. Hover To See A Save's Description And Image. Click To Load A Save.",skin);
        loadTitle.bottom();
        loadTitle.padBottom(50);
        loadTitle.add(loadSubTitle);

        stage.addActor(loadTitle);

        Table exitButton = makeExitBtn();
        stage.addActor(exitButton);

    }


    private Table makeLoadSlots(){

        Table loadSlots = new Table();
        loadSlots.setFillParent(true);
        loadSlots.setWidth(500);
        loadSlots.padTop(30f);
        Table slots = new Table();


        TextButton save1Btn = new TextButton("Save Slot 1", skin);
        TextButton save2Btn = new TextButton("Save Slot 2", skin);
        TextButton save3Btn = new TextButton("Save Slot 3", skin);
        TextButton save4Btn = new TextButton("Save Slot 4", skin);

        table.row();
        table.add(save1Btn).padTop(30f).left();
        table.row();
        table.add(save2Btn).padTop(30f).left();
        table.row();
        table.add(save3Btn).padTop(30f).left();
        table.row();
        table.add(save4Btn).padTop(30f).left();
        table.row();

        loadSlots.add(slots).width(900);


    // Listeners for Save Slots
    save1Btn.addListener(
            new InputListener() {
                //Shows save description when hovering
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (!popupIsOpen) {
                        showSaveDescription();
                    }

                }
                //Removes save description when not hovering
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    destroySaveDescription();
                }
                // Loads the selected save
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (!popupIsOpen) {
                        confirmLoad();
                        destroySaveDescription();
                        return true;
                    }
                    return false;
                }
            });

        save2Btn.addListener(
                new InputListener() {
                    //Shows save description when hovering
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (!popupIsOpen) {
                            showSaveDescription();
                        }
                    }
                    //Removes save description when not hovering
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        destroySaveDescription();
                    }
                    // Loads the selected save
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupIsOpen) {
                            confirmLoad();
                            destroySaveDescription();
                            return true;
                        }
                        return false;
                    }
                });

        save3Btn.addListener(
                new InputListener() {
                    //Shows save description when hovering
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (!popupIsOpen) {
                            showSaveDescription();
                        }
                    }
                    //Removes save description when not hovering
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        destroySaveDescription();
                    }
                    // Loads the selected save
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupIsOpen) {
                            confirmLoad();
                            destroySaveDescription();
                            return true;
                        }
                        return false;
                    }
                });

        save4Btn.addListener(
                new InputListener() {
                    //Shows save description when hovering
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (!popupIsOpen) {
                            showSaveDescription();
                        }
                    }
                    //Removes save description when not hovering
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        destroySaveDescription();
                    }
                    // Loads the selected save
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!popupIsOpen) {
                            confirmLoad();
                            destroySaveDescription();
                            return true;
                        }
                        return false;
                    }
                });


        return loadSlots;
    }



    private void confirmLoad() {

        popupIsOpen = true;
        Image confirmLoadBG =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu/popupBG.png", Texture.class));

        Texture confirmTexture = new Texture(Gdx.files.internal("images/menu/confirmBtn.png"));
        Drawable confirmBtnImage = new TextureRegionDrawable(new TextureRegion(confirmTexture));

        Texture cancelTexture = new Texture(Gdx.files.internal("images/menu/cancelBtn.png"));
        Drawable cancelBtnImage = new TextureRegionDrawable(new TextureRegion(cancelTexture));

        Texture deleteTexture = new Texture(Gdx.files.internal("images/menu/deleteBtn.png"));
        Drawable deleteBtnImage = new TextureRegionDrawable(new TextureRegion(deleteTexture));


        popupBGTable = new Table();
        popupBGTable.setFillParent(true);
        popupBGTable.add(confirmLoadBG).height(300)
                .width(500);


        confirmPopup = new Table();
        confirmPopup.setFillParent(true);



        ImageButton confirmBtn = new ImageButton(confirmBtnImage);
        confirmPopup.add(confirmBtn).padRight(15f);

        ImageButton cancelBtn = new ImageButton(cancelBtnImage);
        confirmPopup.add(cancelBtn).padRight(15f);

        ImageButton deleteBtn = new ImageButton(deleteBtnImage);
        confirmPopup.add(deleteBtn);



        confirmBtn.addListener(
                new InputListener() {
                    //Shows action description when hovering
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        showActionDescription();
                    }
                    //Removes action description when not hovering
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        destroyActionDescription();
                    }
                    // Loads the selected save
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        loadSavedGame();
                        destroyConfirmPopup();
                        return true;

                    }
                });

        cancelBtn.addListener(
                new InputListener() {
                    //Shows action description when hovering
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        showActionDescription();
                    }
                    //Removes action description when not hovering
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        destroyActionDescription();
                    }
                    // Loads the selected save
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        destroyConfirmPopup();
                        return true;
                    }
                });

        deleteBtn.addListener(
                new InputListener() {
                    //Shows action description when hovering
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        showActionDescription();
                    }
                    //Removes action description when not hovering
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        destroyActionDescription();
                    }
                    // Loads the selected save
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        deleteSave();
                        destroyConfirmPopup();
                        return true;
                    }
                });

        stage.addActor(popupBGTable);
        stage.addActor(confirmPopup);
    }



    private void showActionDescription() {

    }

    private void deleteSave() {

    }

    private void destroyActionDescription() {
    }

    private void destroyConfirmPopup() {
        super.dispose();
        confirmPopup.remove();
        popupBGTable.remove();
        popupIsOpen = false;
    }

    private void loadSavedGame() {
        //-TODO launches game with saved variables (time remaining, npc characters voted out)
    }

    private Table showSaveDescription() {

        Image emptySlot =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu/EmptySaveSlot.png", Texture.class));

        descriptionTable = new Table();
        descriptionTable.setFillParent(true);

        Label description = new Label(getSaveDescription(), skin);
        description.setFontScale(1f);
        descriptionTable.add(emptySlot).padRight(20);

        descriptionTable.add(description).pad(5);



        stage.addActor(descriptionTable);

        return descriptionTable;

    }

    private String getSaveDescription() {

        //-TODO return save specific information, Else return:


        return "This Save Slot is Empty!";
    }


    private void destroySaveDescription() {
        super.dispose();
        descriptionTable.remove();
    }

    private void exit() {
        game.setScreen(ScreenType.MAIN_MENU);
    }

    private Table makeExitBtn() {
        TextButton exitBtn = new TextButton("Exit", skin);

        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exit();
                    }
                });

        Table table = new Table();
        table.add(exitBtn).expandX().left().pad(0f, 100f, 100f, 0f);
        return table;
    }


    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        rootTable.clear();
        super.dispose();
    }
}
