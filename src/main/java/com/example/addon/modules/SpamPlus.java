package com.example.addon.modules;

import com.example.addon.Addon;
import com.google.common.collect.Lists;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

public class SpamPlus extends Module {
    public enum Text {
        RandomFacts
    }
//needa find a way to make it so it can be random
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<Enum<Text>> text = sgGeneral.add(new EnumSetting.Builder<Enum<Text>>()
        .name("text")
        .defaultValue(Text.RandomFacts)
        .onChanged(e -> messageI = 0)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay between specified messages in ticks.")
        .defaultValue(20)
        .min(0)
        .sliderMax(200)
        .build()
    );

    private int messageI, timer;

    public SpamPlus() {
        super(Addon.CATEGORY, "spam-plus", "Better than spam.");
    }

    @Override
    public void onActivate() {
        timer = delay.get();
        messageI = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (timer <= 0) {
            String sendMessage = RANDOM_FACTS.get(new Random().nextInt((int) (RANDOM_FACTS.size())));
            ChatUtils.sendPlayerMsg(sendMessage);
            timer = delay.get();
        } else {
            timer--;
        }
    }
    private static final List<String> RANDOM_FACTS = Lists.newArrayList(
        "The Eiffel Tower can grow up to 6 inches (15 centimeters) taller during the summer due to the expansion of iron in heat.",
        "Honey never spoils. Archaeologists have found pots of honey in ancient Egyptian tombs that are over 3,000 years old and still perfectly edible.",
        "The shortest war in history lasted for only 38 minutes. It was between Britain and Zanzibar on August 27, 1896.",
        "Octopuses have three hearts: two pump blood to the gills, and one pumps blood to the rest of the body.",
        "The Great Wall of China is not visible from space with the naked eye, contrary to popular belief. It can be seen from low Earth orbit, but so can many other large structures.",
        "The national animal of Scotland is the unicorn.The entire world's population could fit inside Los Angeles if it had the same population density as New York City.",
        "Bananas are berries, while strawberries are not considered berries but rather \"aggregate fruits.\"",
        "Cleopatra, the last pharaoh of Egypt, lived closer in time to the Moon landing than to the construction of the Great Pyramid of Giza.",
        "Astronauts cannot burp in space because there is no gravity to separate gas from liquid in their stomachs.",
        "A group of flamingos is called a \"flamboyance.\"\n",
        "The coldest temperature ever recorded on Earth was -128.6 degrees Fahrenheit (-89.2 degrees Celsius) in Antarctica.",
        "Humans share 50% of their DNA with bananas.",
        "The shortest war in American history was the Toledo War between Ohio and Michigan in 1835, which lasted about 30 minutes and had no casualties.",
        "A day on Venus is longer than a year on Venus. Venus takes about 243 Earth days to rotate on its axis but only about 225 Earth days to orbit the Sun.\n",
        "The electric chair was invented by a dentist, Dr. Alfred Southwick, in 1881.",
        "The word \"nerd\" was first coined by Dr. Seuss in his book \"If I Ran the Zoo\" in 1950.",
        "A group of owls is called a \"parliament.\"",
        "Cows have best friends and can become stressed when separated from them.",
        "The Earth's oceans contain enough gold to cover the entire planet in a knee-deep layer.",
        "The fingerprints of koala bears are so similar to humans that they have been mistaken at crime scenes.",
        "The tongue of a blue whale can weigh as much as an elephant, and its heart can be as large as a small car.",
        "The longest time between two twins being born is 87 days.",
        "The name \"LEGO\" is derived from the Danish words \"leg godt,\" which mean \"play well.\"",
        "The average person will spend six months of their life waiting for red lights to turn green.",
        "There are more possible iterations of a game of chess than there are atoms in the observable universe.",
        "\"Stewardesses\" is the longest word that is typed with only the left hand on a QWERTY keyboard.",
        "The national animal of Australia is the red kangaroo.",
        "The Hawaiian alphabet only has 12 letters: A, E, I, O, U, H, K, L, M, N, P, and W.",
        "Honeybees communicate with each other through a dance known as the \"waggle dance\" to indicate the direction and distance to a food source.",
        "The dots on dice are called \"pips.\"",
        "The average person will walk the equivalent of three times around the world in their lifetime.",
        "The tongue of a giraffe can be up to 18 inches long.",
        "The sentence \"The quick brown fox jumps over the lazy dog\" uses every letter of the alphabet at least once and is known as a pangram.",
        "The first known contraceptive was crocodile dung, used by ancient Egyptians.",
        "The shortest war in history with casualties was the Anglo-Zanzibar War in 1896, which lasted 38 minutes and resulted in over 500 deaths.",
        "Wombat poop is cube-shaped.",
        "The \"zip\" in \"zip code\" stands for \"Zone Improvement Plan.\"",
        "There are more possible iterations of a standard deck of 52 playing cards than there are atoms on Earth.",
        "There is a sea creature called the \"immortal jellyfish\" that can revert to its earlier life stage after reaching maturity and potentially live forever.",
        " Animals that lay eggs don’t have belly buttons.",
        "Mr. Potato Head was the first toy to be advertised on TV.",
        "Boanthropy is the psychological disorder in which patients believe they are a cow.",
        " Camels have three eyelids.",
        "There is a Mcdonald's on every continent except Antarctica."
// 45 facts for now

    );
}
