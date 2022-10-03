package com.deco2800.game.components.npc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DialogWithSelection {
    private final String dialog;
    private DialogWithSelection next;
    private DialogWithSelection option1;
    private DialogWithSelection option2;

    public DialogWithSelection(String dialog) {
        this.dialog = dialog;
    }

    public String getDialog() {
        return dialog;
    }

    public DialogWithSelection getNext() {
        return next;
    }

    public DialogWithSelection getOption1() {
        return option1;
    }

    public DialogWithSelection getOption2() {
        return option2;
    }

    public boolean isSelectionPoint() {
        return getOption1() != null && getOption2() != null;
    }

    public DialogWithSelection getLastDialog() {
        DialogWithSelection lastDialog = this;
        while (lastDialog.getNext() != null) {
            lastDialog = lastDialog.getNext();
        }
        return lastDialog;
    }

    public void setNext(DialogWithSelection next) {
        this.next = next;
    }

    public void setOption1(DialogWithSelection option1) {
        this.option1 = option1;
    }

    public void setOption2(DialogWithSelection option2) {
        this.option2 = option2;
    }

    public static ArrayList<String> readOptionDialogs(int chapterNum, int selectionNum, int option) throws IOException {
        BufferedReader br;
        ArrayList<String> dialogs = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("dialogs/Chapter " + chapterNum
                    + "/Chap" + chapterNum + "_option" + selectionNum + "_" + option + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                dialogs.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dialogs;
    }

    public void setSelectionPoint(String option1Text, String option2Text,
                                  int chapterNum, int selectionNum) {
        // set selection point for root
        DialogWithSelection selectionPoint = new DialogWithSelection(null);
        DialogWithSelection option1 = new DialogWithSelection(option1Text);
        DialogWithSelection option2 = new DialogWithSelection(option2Text);
        this.setNext(selectionPoint);
        selectionPoint.setOption1(option1);
        selectionPoint.setOption2(option2);

        // set follow-up dialogs for 1st selection
        try {
            for (String dialog : readOptionDialogs(chapterNum, selectionNum, 1)) {
                option1.setNext(new DialogWithSelection(dialog));
                option1 = option1.getNext();
            }
            for (String dialog : readOptionDialogs(chapterNum, selectionNum, 2)) {
                option2.setNext(new DialogWithSelection(dialog));
                option2 = option2.getNext();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DialogWithSelection)) return false;
        DialogWithSelection other = (DialogWithSelection) o;
        return Objects.equals(dialog, other.dialog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dialog);
    }

    public static DialogWithSelection getChapter2Dialog() {
        DialogWithSelection pointer;

        // set root dialog
        DialogWithSelection root = new DialogWithSelection("Zoe: \"Get the wetsuit! It's water everywhere!\"");

        // set selection point for root (1st selection)
        root.setSelectionPoint("Find the wetsuit.", "Go to the village first.",
                2, 1);

        // set selection point for 2nd selection
        pointer = root.getNext().getOption2().getLastDialog();
        DialogWithSelection selection2 = pointer;
        selection2.setSelectionPoint("Ignore this woman.",
                "Try to get clues from this woman.",
                2, 2);

        // set selection point for 3rd selection
        pointer = selection2.getNext().getOption2().getLastDialog();
        DialogWithSelection selection3 = pointer;
        selection3.setSelectionPoint("Ask for information about that person's voice.",
                "Ask for information about that person's appearance.",
                2, 3);

        // set selection points for 4th selection
        pointer = selection3.getNext().getOption1().getLastDialog();
        DialogWithSelection selection4FromVoice = pointer;
        selection4FromVoice.setSelectionPoint("Tell her the truth.",
                "Temporarily hide the truth.", 2, 4);

        pointer = selection3.getNext().getOption2().getLastDialog();
        DialogWithSelection selection4FromAppearance = pointer;
        selection4FromAppearance.setSelectionPoint("Tell her the truth.",
                "Temporarily hide the truth.", 2, 4);

        // return the whole dialogs
        return root;
    }

    public static ArrayList<DialogWithSelection> getChapter2Endings() {
        ArrayList<DialogWithSelection> endings = new ArrayList<>();
        DialogWithSelection end1 = getChapter2Dialog().getNext().getOption2().getLastDialog()
                .getOption2().getLastDialog()
                .getOption1().getLastDialog()
                .getOption1().getLastDialog();
        DialogWithSelection end2 = getChapter2Dialog().getNext().getOption2().getLastDialog()
                .getOption2().getLastDialog()
                .getOption1().getLastDialog()
                .getOption2().getLastDialog();
        DialogWithSelection end3 = getChapter2Dialog().getNext().getOption2().getLastDialog()
                .getOption2().getLastDialog()
                .getOption2().getLastDialog()
                .getOption1().getLastDialog();
        DialogWithSelection end4 = getChapter2Dialog().getNext().getOption2().getLastDialog()
                .getOption2().getLastDialog()
                .getOption2().getLastDialog()
                .getOption2().getLastDialog();
        endings.add(end1);
        endings.add(end2);
        endings.add(end3);
        endings.add(end4);
        return endings;
    }

    public static DialogWithSelection getChapter3Dialog() {
        DialogWithSelection pointer;

        // set root dialog
        DialogWithSelection root = new DialogWithSelection("You: \"Time to decide whether to go back to"
        + "find Metis or move on in the village.\"");

        // set selection point for root (1st selection)
        root.setSelectionPoint("Back to find Metis.", "Move on in the village.",
                3, 1);

        // set selection points for 2nd selection
        pointer = root.getNext().getOption1().getLastDialog();
        DialogWithSelection selection2From1 = pointer;
        selection2From1.setSelectionPoint("Observe from the sidelines.",
                "Stop their quarrel.", 3, 2);

        pointer = root.getNext().getOption2().getLastDialog();
        DialogWithSelection selection2From2 = pointer;
        selection2From2.setSelectionPoint("Observe from the sidelines.",
                "Stop their quarrel.", 3, 2);

        // set selection points for 3rd selection
        pointer = selection2From1.getNext().getOption2().getLastDialog();
        DialogWithSelection selection3From1 = pointer;
        selection3From1.setSelectionPoint("Tell him the truth of Nereus' death.",
                "Conceal the situation.", 3, 3);

        pointer = selection2From2.getNext().getOption2().getLastDialog();
        DialogWithSelection selection3From2 = pointer;
        selection3From2.setSelectionPoint("Tell him the truth of Nereus' death.",
                "Conceal the situation.", 3, 3);

        // set selection points for 4th selection
        pointer = selection3From1.getNext().getOption1().getLastDialog();
        DialogWithSelection selection4From1_1 = pointer;
        selection4From1_1.setSelectionPoint("Ignore this person.",
                "Find a way to follow Orpheus back to the theater.", 3, 4);

        pointer = selection3From1.getNext().getOption2().getLastDialog();
        DialogWithSelection selection4From1_2 = pointer;
        selection4From1_2.setSelectionPoint("Ignore this person.",
                "Find a way to follow Orpheus back to the theater.", 3, 4);

        pointer = selection3From2.getNext().getOption1().getLastDialog();
        DialogWithSelection selection4From2_1 = pointer;
        selection4From2_1.setSelectionPoint("Ignore this person.",
                "Find a way to follow Orpheus back to the theater.", 3, 4);

        pointer = selection3From2.getNext().getOption2().getLastDialog();
        DialogWithSelection selection4From2_2 = pointer;
        selection4From2_2.setSelectionPoint("Ignore this person.",
                "Find a way to follow Orpheus back to the theater.", 3, 4);

        // return the whole dialogs
        return root;
    }
}