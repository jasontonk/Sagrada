package model;

import java.util.ArrayList;


public class PublicObjectiveCard {
    private int id;
    private String name;
    private String description;
    private int points;


    public PublicObjectiveCard(int id, String name, String description, int points) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.points = points;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public int getPoints() {
        return points;
    }


    public void setPoints(int points) {
        this.points = points;
    }


    public int calculateScore(Board board) {
        switch (id) {
            case 1:
            	return calculateShadeVariety(board, points);
            case 2:
            	return calculatePairShades(board, 3, 4, points);
            case 3:
            	return calculateColumnShadeVariety(board, points);
            case 4:
            	return calculateColumnColorVariety(board, points);
            case 5:
            	return calculatePairShades(board, 5, 6, points);
            case 6:
                return calculateColorVariety(board, points);
            case 7:
            	return calculateRowColorVariety(board, points);
            case 8:
            	return calculateColorDiagonals(board, points);
            case 9:
                return calculatePairShades(board, 1, 2, points);
            case 10:
            	return calculateRowShadeVariety(board, points);
            default:
                return 0;
        }
    }


    private int calculatePairShades(Board board, int val1, int val2, int rewardScore) {
        int score = 0;

        ArrayList<Integer> valueList = new ArrayList<>();
        for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) { 
            for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) { 
                if (board.getBoardField(x, y).hasDie()) { 
                    int value = board.getBoardField(x, y).getDie().getEyes();
                    if (value == val1 || value == val2) { 
                        valueList.add(value);
                    }
                }
            }
        }

        outerloop:
        for (int x = 0; x < valueList.size(); ) {
            int value = valueList.get(x);
            for (int i = 1; i < valueList.size(); i++) {
                int value1 = valueList.get(i); 
                if (value == value1) { 
                    score += rewardScore; 
                    valueList.remove(Integer.valueOf(value)); 
                    valueList.remove(Integer.valueOf(value1)); 
                    continue outerloop;
                }
            }
            valueList.remove(Integer.valueOf(value));
        }
        return score;
    }


    private int calculateShadeVariety(Board board, int rewardScore) {
        int score = 0;

        ArrayList<Integer> valueList = new ArrayList<>();
        for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) { 
            for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) { 
                if (board.getBoardField(x, y).hasDie()) { 
                    valueList.add(board.getBoardField(x, y).getDie().getEyes()); 
                }
            }
        }

        ArrayList<Integer> varietyList = new ArrayList<>();
        for (int x = 0; x < valueList.size(); ) {
            int firstValue = valueList.get(x); 
            varietyList.add(firstValue); 

            for (int i = 1; i < valueList.size(); i++) {
                int nextValue = valueList.get(i); 
                if (varietyList.contains(nextValue)) {
                    continue; 
                }
                varietyList.add(nextValue); 
            }

            if (varietyList.contains(1) && varietyList.contains(2) && varietyList.contains(3)
                    && varietyList.contains(4) && varietyList.contains(5)
                    && varietyList.contains(6)) { 
                score += rewardScore; 
                for (int removeValue : varietyList) { 
                    valueList.remove(Integer.valueOf(removeValue));
                }
                varietyList.clear(); 
            } else {
                break;
            }
        }

        return score;
    }


    private int calculateRowShadeVariety(Board board, int rewardScore) {
        int score = 0;

        outerloop:
        for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) { 
            ArrayList<Integer> valueList = new ArrayList<>();
            for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) { 
                if (board.getBoardField(x, y).hasDie()) { 
                    valueList.add(board.getBoardField(x, y).getDie().getEyes()); 
                }
            }

            ArrayList<Integer> varietyList = new ArrayList<>();
            for (int i = 0; i < valueList.size(); i++) { 
                int value = valueList.get(i);
                if (varietyList.contains(value)) { 
                    continue outerloop; 
                }
                varietyList.add(value); 
            }

            if (varietyList.size() == 5) { 
                score += rewardScore; 
            }
        }

        return score;
    }


    private int calculateRowColorVariety(Board board, int rewardScore) {
        int score = 0;

        outerloop:
        for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
            ArrayList<ModelColor> colorList = new ArrayList<>();
            for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) {
                if (board.getBoardField(x, y).hasDie()) {
                    colorList.add(board.getBoardField(x, y).getDie().getColor()); 
                }
            }

            ArrayList<ModelColor> varietyList = new ArrayList<>();
            for (int i = 0; i < colorList.size(); i++) { 
            	ModelColor color = colorList.get(i); 
                if (varietyList.contains(color)) { 
                    continue outerloop;
                }
                varietyList.add(color); 
            }

            if (varietyList.size() == 5) { 
                score += rewardScore; 
            }
        }

        return score;
    }


    private int calculateColorVariety(Board board, int rewardScore) {
        int score = 0;

        ArrayList<ModelColor> colorList = new ArrayList<>();
        for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) { 
            for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
                if (board.getBoardField(x, y).hasDie()) { 
                    colorList.add(board.getBoardField(x, y).getDie().getColor()); 
                }
            }
        }

        
        ArrayList<ModelColor> varietyList = new ArrayList<>();
        for (int x = 0; x < colorList.size(); ) {
        	ModelColor firstColor = colorList.get(x); 
            varietyList.add(firstColor); 

            for (int i = 1; i < colorList.size(); i++) {
            	ModelColor nextColor = colorList.get(i); 
                if (varietyList.contains(nextColor)) {
                    continue; 
                }
                varietyList.add(nextColor);
            }

            if (varietyList.contains(ModelColor.RED) && varietyList.contains(ModelColor.BLUE) && varietyList
                    .contains(ModelColor.GREEN) && varietyList.contains(ModelColor.PURPLE) && varietyList
                    .contains(ModelColor.YELLOW)) { 
                score = score + rewardScore; 
                for (ModelColor removeColor : varietyList) { 
                    colorList.remove(removeColor);
                }
                varietyList.clear(); 
            } else {
                break;
            }
        }

        return score;
    }


    private int calculateColumnShadeVariety(Board board, int rewardScore) {
        int score = 0;

        outerloop:
        for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) { 
            ArrayList<Integer> valueList = new ArrayList<>();
            for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) { 
                if (board.getBoardField(x, y).hasDie()) { 
                    valueList.add(board.getBoardField(x, y).getDie().getEyes()); 
                }
            }

            ArrayList<Integer> varietyList = new ArrayList<>();
            for (int i = 0; i < valueList.size(); i++) { 
                int value = valueList.get(i); 
                if (varietyList.contains(value)) {
                    continue outerloop;
                }
                varietyList.add(value);
            }

            if (varietyList.size() == 4) { 
                score = score + rewardScore; 
            }
        }

        return score;
    }


    private int calculateColumnColorVariety(Board board, int rewardScore) {
        int score = 0;

        outerloop:
        for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) { 
            ArrayList<ModelColor> colorList = new ArrayList<>();
            for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
                if (board.getBoardField(x, y).hasDie()) { 
                    colorList.add(board.getBoardField(x, y).getDie().getColor()); 
                }
            }

            ArrayList<ModelColor> varietyList = new ArrayList<>();
            for (int i = 0; i < colorList.size(); i++) { 
            	ModelColor color = colorList.get(i); 
                if (varietyList.contains(color)) { 
                    continue outerloop;
                }
                varietyList.add(color);
            }

            if (varietyList.size() == 4) {
                score = score + rewardScore; 
            }
        }

        return score;
    }


    private int calculateColorDiagonals(Board board, int rewardScore) {
        int score = 0;

        ArrayList<BoardField> blockedFields = new ArrayList<>();
        for (int y = 0; y < Board.BOARD_SQUARES_VERTICAL; y++) {
            for (int x = 0; x < Board.BOARD_SQUARES_HORIZONTAL; x++) {
                if (blockedFields.contains(board.getBoardField(x, y))) {
                    continue; 
                }
                if (board.getBoardField(x, y).hasDie()) {
                    BoardField firstBoardField = board.getBoardField(x, y); 
                    blockedFields.add(firstBoardField);

                    ArrayList<BoardField> nextIteration = new ArrayList<>();
                    ArrayList<BoardField> list = checkColorDiagonals(board, firstBoardField); 
                    for (BoardField boardField : list) { 
                        if (!blockedFields.contains(boardField)) { 
                            blockedFields.add(boardField);
                            nextIteration.add(boardField); 
                        }
                    }

                    if (nextIteration.size() == 0) { 
                        blockedFields.remove(firstBoardField); 
                        continue; 
                    }

                    for (int i = 0; i < nextIteration.size(); ) { 
                        BoardField patternCardField = nextIteration.get(i);
                        ArrayList<BoardField> listNext = checkColorDiagonals(board, patternCardField);
                        for (BoardField boardFieldNext : listNext) {
                            if (!blockedFields.contains(boardFieldNext)) {
                                blockedFields.add(boardFieldNext);
                                nextIteration.add(boardFieldNext);
                            }
                        }
                        nextIteration.remove(patternCardField);
                    }
                }
            }
        }

        for (BoardField boardField : blockedFields) {
            score = score + rewardScore; 
        }
        return score;
    }


    private ArrayList<BoardField> checkColorDiagonals(Board board, BoardField boardField) {
        ArrayList<BoardField> list = new ArrayList<>();

        BoardField boardFieldNE = board.checkNorthEastDieColor(boardField, boardField.getDie().getColor());
        if (boardFieldNE != null) {
            list.add(boardFieldNE);
        }

        BoardField boardFieldSE = board.checkSouthEastDieColor(boardField,boardField.getDie().getColor());
        if (boardFieldSE != null) {
            list.add(boardFieldSE);
        }

        BoardField boardFieldSW = board.checkSouthWestDieColor(boardField,boardField.getDie().getColor());
        if (boardFieldSW != null) {
            list.add(boardFieldSW);
        }

        BoardField boardFieldNW = board.checkNorthWestDieColor(boardField,boardField.getDie().getColor());
        if (boardFieldNW != null) {
            list.add(boardFieldNW);
        }

        return list;
    }
}
