package com.example.catanserver.businessLogic.model.building;


public interface BuildStructure { //todo: should allow generic input: Knot, Edge (for method selectBuildingSite(<T>)
    //instantiate PlayerImpl and Knot as private variables
    boolean checkResources(); //prerequisite to being able to buy a structure
    void selectBuildingSite(); //prerequisite to being allowed to build a structure at a certain site
    void updateGameboard();
    void updatePlayerInventory();
}
