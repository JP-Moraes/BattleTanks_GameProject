package com.example;

public enum Arena {
    DESERT("Crimson Dunes Zone"),
    RUINED_CITY("Bullet Road Zone"),
    RAINFOREST("Gigaleaf Zone");

    private final String name;


    Arena(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}

