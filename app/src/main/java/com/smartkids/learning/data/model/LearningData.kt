package com.smartkids.learning.data.model

object LearningData {

    val abcItems: List<ABCItem> = listOf(
        ABCItem("A", "Apple"), ABCItem("B", "Ball"), ABCItem("C", "Cat"),
        ABCItem("D", "Dog"), ABCItem("E", "Elephant"), ABCItem("F", "Fish"),
        ABCItem("G", "Giraffe"), ABCItem("H", "House"), ABCItem("I", "Ice Cream"),
        ABCItem("J", "Jellyfish"), ABCItem("K", "Kite"), ABCItem("L", "Lion"),
        ABCItem("M", "Monkey"), ABCItem("N", "Nest"), ABCItem("O", "Orange"),
        ABCItem("P", "Penguin"), ABCItem("Q", "Queen"), ABCItem("R", "Rainbow"),
        ABCItem("S", "Star"), ABCItem("T", "Tiger"), ABCItem("U", "Umbrella"),
        ABCItem("V", "Violin"), ABCItem("W", "Whale"), ABCItem("X", "Xylophone"),
        ABCItem("Y", "Yak"), ABCItem("Z", "Zebra")
    )

    val numberItems: List<NumberItem> = (0..20).map { num ->
        val words = listOf("Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten",
            "Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty")
        NumberItem(num, words[num])
    }

    val shapeItems: List<ShapeItem> = listOf(
        ShapeItem("Circle", 0, "A round shape with no corners"),
        ShapeItem("Square", 4, "A shape with 4 equal sides"),
        ShapeItem("Triangle", 3, "A shape with 3 sides"),
        ShapeItem("Rectangle", 4, "A shape with 4 sides, opposite sides equal"),
        ShapeItem("Oval", 0, "A stretched circle shape"),
        ShapeItem("Diamond", 4, "A square turned sideways"),
        ShapeItem("Star", 10, "A shape that looks like a star in the sky"),
        ShapeItem("Heart", 0, "A shape that looks like a heart"),
        ShapeItem("Hexagon", 6, "A shape with 6 sides"),
        ShapeItem("Pentagon", 5, "A shape with 5 sides"),
        ShapeItem("Octagon", 8, "A shape with 8 sides, like a stop sign"),
        ShapeItem("Crescent", 0, "A moon-shaped curve")
    )

    val colorItems: List<ColorItem> = listOf(
        ColorItem("Red", "#EF476F", "Apple"), ColorItem("Blue", "#00B4D8", "Sky"),
        ColorItem("Green", "#06D6A0", "Grass"), ColorItem("Yellow", "#FFD166", "Sun"),
        ColorItem("Orange", "#FF9F1C", "Orange fruit"), ColorItem("Purple", "#7B2FF7", "Grape"),
        ColorItem("Pink", "#FF6B9D", "Rose"), ColorItem("Brown", "#8B4513", "Chocolate"),
        ColorItem("Black", "#2D3436", "Night"), ColorItem("White", "#FFFFFF", "Cloud"),
        ColorItem("Gray", "#636E72", "Stone"), ColorItem("Cyan", "#00CED1", "Ocean")
    )

    val animalItems: List<AnimalItem> = listOf(
        AnimalItem("Lion", "Roar", "The king of the jungle", "Savanna"),
        AnimalItem("Elephant", "Trumpet", "The largest land animal", "Forest"),
        AnimalItem("Dog", "Bark", "Man's best friend", "Home"),
        AnimalItem("Cat", "Meow", "A playful pet", "Home"),
        AnimalItem("Cow", "Moo", "Gives us milk", "Farm"),
        AnimalItem("Horse", "Neigh", "A fast running animal", "Farm"),
        AnimalItem("Rabbit", "Squeak", "Has long ears", "Forest"),
        AnimalItem("Monkey", "Chatter", "Loves bananas", "Jungle"),
        AnimalItem("Tiger", "Growl", "Has stripes on its body", "Forest"),
        AnimalItem("Bear", "Growl", "A big, strong animal", "Forest"),
        AnimalItem("Deer", "Bellow", "Has beautiful antlers", "Forest"),
        AnimalItem("Fox", "Bark", "A clever animal", "Forest"),
        AnimalItem("Giraffe", "Hum", "The tallest animal", "Savanna"),
        AnimalItem("Zebra", "Bark", "Has black and white stripes", "Savanna"),
        AnimalItem("Penguin", "Squawk", "A bird that cannot fly", "Antarctica"),
        AnimalItem("Dolphin", "Click", "A smart sea animal", "Ocean"),
        AnimalItem("Frog", "Croak", "Can jump very far", "Pond"),
        AnimalItem("Snake", "Hiss", "A legless reptile", "Various"),
        AnimalItem("Parrot", "Squawk", "Can mimic human speech", "Jungle"),
        AnimalItem("Turtle", "Hiss", "Carries its home on its back", "Various")
    )

    val fruitItems: List<FruitItem> = listOf(
        FruitItem("Apple", "Red", "Sweet and crunchy"), FruitItem("Banana", "Yellow", "Soft and sweet"),
        FruitItem("Orange", "Orange", "Juicy and tangy"), FruitItem("Grapes", "Purple", "Small and sweet"),
        FruitItem("Mango", "Yellow", "Sweet and tropical"), FruitItem("Watermelon", "Green", "Juicy and refreshing"),
        FruitItem("Strawberry", "Red", "Sweet with tiny seeds"), FruitItem("Pineapple", "Yellow", "Sweet and tangy"),
        FruitItem("Peach", "Peach", "Soft and fuzzy"), FruitItem("Pear", "Green", "Sweet and juicy"),
        FruitItem("Cherry", "Red", "Small and sweet"), FruitItem("Blueberry", "Blue", "Small and healthy"),
        FruitItem("Lemon", "Yellow", "Sour and tangy"), FruitItem("Coconut", "Brown", "Has water inside"),
        FruitItem("Papaya", "Orange", "Tropical and sweet")
    )

    val vegetableItems: List<VegetableItem> = listOf(
        VegetableItem("Carrot", "Orange", "Good for eyes"), VegetableItem("Potato", "Brown", "Gives energy"),
        VegetableItem("Tomato", "Red", "Rich in vitamins"), VegetableItem("Onion", "White", "Adds flavor"),
        VegetableItem("Broccoli", "Green", "Very healthy"), VegetableItem("Corn", "Yellow", "Sweet and crunchy"),
        VegetableItem("Pea", "Green", "Small and round"), VegetableItem("Cabbage", "Green", "Good for digestion"),
        VegetableItem("Spinach", "Green", "Makes you strong"), VegetableItem("Capsicum", "Red", "Crunchy and colorful"),
        VegetableItem("Cucumber", "Green", "Cool and refreshing"), VegetableItem("Radish", "Red", "Spicy and crunchy"),
        VegetableItem("Pumpkin", "Orange", "Sweet and nutritious"), VegetableItem("Beans", "Green", "Good for health"),
        VegetableItem("Mushroom", "White", "Good source of protein")
    )

    val birdItems: List<BirdItem> = listOf(
        BirdItem("Sparrow", "Chirp", true), BirdItem("Eagle", "Screech", true),
        BirdItem("Parrot", "Squawk", true), BirdItem("Crow", "Caw", true),
        BirdItem("Pigeon", "Coo", true), BirdItem("Owl", "Hoot", true),
        BirdItem("Peacock", "Scream", true), BirdItem("Duck", "Quack", true),
        BirdItem("Hen", "Cluck", false), BirdItem("Penguin", "Squawk", false),
        BirdItem("Ostrich", "Boom", false), BirdItem("Flamingo", "Honk", true),
        BirdItem("Woodpecker", "Drum", true), BirdItem("Robin", "Cheerily", true),
        BirdItem("Swan", "Hiss", true)
    )

    val vehicleItems: List<VehicleItem> = listOf(
        VehicleItem("Car", 4, "Driving on roads"), VehicleItem("Bus", 6, "Public transport"),
        VehicleItem("Bicycle", 2, "Riding for fun"), VehicleItem("Motorcycle", 2, "Fast two-wheeler"),
        VehicleItem("Train", 0, "Traveling long distances"), VehicleItem("Airplane", 0, "Flying in the sky"),
        VehicleItem("Boat", 0, "Sailing on water"), VehicleItem("Truck", 6, "Carrying goods"),
        VehicleItem("Helicopter", 0, "Flying with rotor blades"), VehicleItem("Ambulance", 4, "Emergency transport"),
        VehicleItem("Fire Truck", 6, "Putting out fires"), VehicleItem("Rocket", 0, "Going to space")
    )

    val flowerItems: List<FlowerItem> = listOf(
        FlowerItem("Rose", "Red", "Spring"), FlowerItem("Sunflower", "Yellow", "Summer"),
        FlowerItem("Lotus", "Pink", "Summer"), FlowerItem("Tulip", "Various", "Spring"),
        FlowerItem("Daisy", "White", "Spring"), FlowerItem("Lily", "White", "Summer"),
        FlowerItem("Jasmine", "White", "Summer"), FlowerItem("Marigold", "Orange", "Autumn"),
        FlowerItem("Orchid", "Purple", "Spring"), FlowerItem("Hibiscus", "Red", "Summer"),
        FlowerItem("Daffodil", "Yellow", "Spring"), FlowerItem("Poppy", "Red", "Summer")
    )

    val bodyPartItems: List<BodyPartItem> = listOf(
        BodyPartItem("Head", 1, "Thinking"), BodyPartItem("Eyes", 2, "Seeing"),
        BodyPartItem("Ears", 2, "Hearing"), BodyPartItem("Nose", 1, "Smelling"),
        BodyPartItem("Mouth", 1, "Eating and talking"), BodyPartItem("Teeth", 32, "Chewing food"),
        BodyPartItem("Tongue", 1, "Tasting"), BodyPartItem("Hands", 2, "Holding things"),
        BodyPartItem("Fingers", 10, "Picking up things"), BodyPartItem("Arms", 2, "Lifting things"),
        BodyPartItem("Legs", 2, "Walking"), BodyPartItem("Feet", 2, "Standing"),
        BodyPartItem("Shoulders", 2, "Carrying things"), BodyPartItem("Knees", 2, "Bending legs"),
        BodyPartItem("Elbows", 2, "Bending arms")
    )

    val dayItems: List<DayItem> = listOf(
        DayItem("Monday", "Mon", 1), DayItem("Tuesday", "Tue", 2),
        DayItem("Wednesday", "Wed", 3), DayItem("Thursday", "Thu", 4),
        DayItem("Friday", "Fri", 5), DayItem("Saturday", "Sat", 6),
        DayItem("Sunday", "Sun", 7)
    )

    val monthItems: List<MonthItem> = listOf(
        MonthItem("January", "Jan", 31, "Winter"), MonthItem("February", "Feb", 28, "Winter"),
        MonthItem("March", "Mar", 31, "Spring"), MonthItem("April", "Apr", 30, "Spring"),
        MonthItem("May", "May", 31, "Spring"), MonthItem("June", "Jun", 30, "Summer"),
        MonthItem("July", "Jul", 31, "Summer"), MonthItem("August", "Aug", 31, "Summer"),
        MonthItem("September", "Sep", 30, "Autumn"), MonthItem("October", "Oct", 31, "Autumn"),
        MonthItem("November", "Nov", 30, "Autumn"), MonthItem("December", "Dec", 31, "Winter")
    )

    val seasonItems: List<SeasonItem> = listOf(
        SeasonItem("Spring", "March, April, May", "Warm and pleasant", "Flower picking, gardening"),
        SeasonItem("Summer", "June, July, August", "Hot and sunny", "Swimming, eating ice cream"),
        SeasonItem("Autumn", "September, October, November", "Cool and windy", "Collecting leaves, hiking"),
        SeasonItem("Winter", "December, January, February", "Cold and snowy", "Building snowmen, hot chocolate")
    )

    val weatherItems: List<WeatherItem> = listOf(
        WeatherItem("Sunny", "Bright and warm with clear sky", "Sunglasses, hat"),
        WeatherItem("Rainy", "Water falling from clouds", "Raincoat, umbrella"),
        WeatherItem("Cloudy", "Sky covered with clouds", "Light jacket"),
        WeatherItem("Windy", "Air moving fast", "Windbreaker"),
        WeatherItem("Snowy", "White snow falling", "Warm coat, gloves, boots"),
        WeatherItem("Stormy", "Heavy rain with thunder and lightning", "Stay indoors"),
        WeatherItem("Foggy", "Thick mist reducing visibility", "Be careful walking"),
        WeatherItem("Hot", "Very high temperature", "Light clothes, drink water")
    )

    val oppositeItems: List<OppositeItem> = listOf(
        OppositeItem("Big", "Small", "Size"), OppositeItem("Hot", "Cold", "Temperature"),
        OppositeItem("Up", "Down", "Direction"), OppositeItem("Happy", "Sad", "Emotion"),
        OppositeItem("Fast", "Slow", "Speed"), OppositeItem("Heavy", "Light", "Weight"),
        OppositeItem("Tall", "Short", "Height"), OppositeItem("Loud", "Quiet", "Sound"),
        OppositeItem("Wet", "Dry", "Moisture"), OppositeItem("Open", "Close", "State"),
        OppositeItem("Day", "Night", "Time"), OppositeItem("Hard", "Soft", "Texture"),
        OppositeItem("Full", "Empty", "Amount"), OppositeItem("Clean", "Dirty", "Condition"),
        OppositeItem("Old", "New", "Age")
    )

    val goodHabitItems: List<GoodHabitItem> = listOf(
        GoodHabitItem("Brush your teeth", "Brush twice a day", "Keeps teeth healthy"),
        GoodHabitItem("Take a bath daily", "Clean your body with soap", "Keeps you clean"),
        GoodHabitItem("Eat vegetables", "Include vegetables in meals", "Makes you strong"),
        GoodHabitItem("Drink water", "Drink 8 glasses daily", "Keeps you hydrated"),
        GoodHabitItem("Exercise daily", "Play outdoor games", "Makes muscles strong"),
        GoodHabitItem("Sleep early", "Go to bed by 9 PM", "Helps body grow"),
        GoodHabitItem("Wash hands", "Before eating and after playing", "Keeps germs away"),
        GoodHabitItem("Share with others", "Share toys and food", "Makes everyone happy"),
        GoodHabitItem("Say please and thank you", "Use polite words", "Shows good manners"),
        GoodHabitItem("Read books", "Read 15 minutes daily", "Improves knowledge")
    )

    val safetyRules: List<SafetyRuleItem> = listOf(
        SafetyRuleItem("Don't talk to strangers", "Never go with someone you don't know", "Say NO and walk away"),
        SafetyRuleItem("Look both ways", "Check left and right before crossing", "Wait for the signal"),
        SafetyRuleItem("Don't touch fire", "Fire is dangerous and can burn", "Tell an adult if you see fire"),
        SafetyRuleItem("Wear a helmet", "Always wear one while cycling", "Protects your head"),
        SafetyRuleItem("Don't play with sharp objects", "Scissors and knives can hurt", "Ask an adult for help"),
        SafetyRuleItem("Know emergency number", "Call for help in emergency", "Memorize parents' number"),
        SafetyRuleItem("Stay away from water", "Don't go near pools alone", "Always swim with an adult"),
        SafetyRuleItem("Don't share info online", "Never tell name or address online", "Tell parents if asked"),
        SafetyRuleItem("Buckle your seatbelt", "Always wear in a car", "Keeps you safe"),
        SafetyRuleItem("Tell if bullied", "Don't keep it a secret", "Talk to parents or teachers")
    )

    val gkItems: List<GKItem> = listOf(
        GKItem("How many days in a week?", "7", "Time"),
        GKItem("How many months in a year?", "12", "Time"),
        GKItem("What color is the sky?", "Blue", "Nature"),
        GKItem("How many legs does a dog have?", "4", "Animals"),
        GKItem("What do plants need to grow?", "Water and sunlight", "Science"),
        GKItem("What is the largest planet?", "Jupiter", "Space"),
        GKItem("How many colors in a rainbow?", "7", "Nature"),
        GKItem("Hardest natural substance?", "Diamond", "Science"),
        GKItem("Which is the largest ocean?", "Pacific Ocean", "Geography"),
        GKItem("How many continents?", "7", "Geography"),
        GKItem("What gives us light during the day?", "The Sun", "Nature"),
        GKItem("King of the jungle?", "Lion", "Animals"),
        GKItem("What do we breathe?", "Oxygen", "Science"),
        GKItem("How many senses do we have?", "5", "Science"),
        GKItem("Closest planet to the Sun?", "Mercury", "Space"),
        GKItem("What is H2O?", "Water", "Science"),
        GKItem("Tallest mountain?", "Mount Everest", "Geography"),
        GKItem("Bones in an adult body?", "206", "Science"),
        GKItem("Boiling point of water?", "100 degrees Celsius", "Science"),
        GKItem("Most populated country?", "India", "Geography")
    )

    val planetItems: List<PlanetItem> = listOf(
        PlanetItem("Mercury", 1, "Smallest planet, closest to Sun", "A year is just 88 Earth days"),
        PlanetItem("Venus", 2, "Hottest planet", "Spins backwards"),
        PlanetItem("Earth", 3, "Our home with water and life", "Only planet not named after a god"),
        PlanetItem("Mars", 4, "The Red Planet", "Has the tallest volcano"),
        PlanetItem("Jupiter", 5, "Largest planet", "Has 95 known moons"),
        PlanetItem("Saturn", 6, "Famous for its rings", "Rings made of ice and rock"),
        PlanetItem("Uranus", 7, "An ice giant", "Rotates on its side"),
        PlanetItem("Neptune", 8, "Farthest planet", "Strongest winds in solar system")
    )

    val countryItems: List<CountryItem> = listOf(
        CountryItem("India", "New Delhi", "Asia", "Saffron, White, Green"),
        CountryItem("United States", "Washington D.C.", "North America", "Red, White, Blue"),
        CountryItem("United Kingdom", "London", "Europe", "Red, White, Blue"),
        CountryItem("France", "Paris", "Europe", "Blue, White, Red"),
        CountryItem("Japan", "Tokyo", "Asia", "White, Red"),
        CountryItem("Australia", "Canberra", "Oceania", "Blue, Red, White"),
        CountryItem("Brazil", "Brasilia", "South America", "Green, Yellow, Blue"),
        CountryItem("China", "Beijing", "Asia", "Red, Yellow"),
        CountryItem("Germany", "Berlin", "Europe", "Black, Red, Gold"),
        CountryItem("Canada", "Ottawa", "North America", "Red, White"),
        CountryItem("Russia", "Moscow", "Europe/Asia", "White, Blue, Red"),
        CountryItem("South Africa", "Pretoria", "Africa", "Multicolor"),
        CountryItem("Italy", "Rome", "Europe", "Green, White, Red"),
        CountryItem("Mexico", "Mexico City", "North America", "Green, White, Red"),
        CountryItem("Egypt", "Cairo", "Africa", "Red, White, Black"),
        CountryItem("South Korea", "Seoul", "Asia", "White, Red, Blue"),
        CountryItem("Argentina", "Buenos Aires", "South America", "Blue, White"),
        CountryItem("Thailand", "Bangkok", "Asia", "Red, White, Blue"),
        CountryItem("Sweden", "Stockholm", "Europe", "Blue, Yellow"),
        CountryItem("Nigeria", "Abuja", "Africa", "Green, White")
    )

    val rhymeItems: List<RhymeItem> = listOf(
        RhymeItem("Twinkle Twinkle Little Star", listOf(
            "Twinkle, twinkle, little star,", "How I wonder what you are!",
            "Up above the world so high,", "Like a diamond in the sky.",
            "Twinkle, twinkle, little star,", "How I wonder what you are!"
        )),
        RhymeItem("Baa Baa Black Sheep", listOf(
            "Baa, baa, black sheep,", "Have you any wool?",
            "Yes, sir, yes, sir,", "Three bags full;",
            "One for the master,", "One for the dame,",
            "And one for the little boy", "Who lives down the lane."
        )),
        RhymeItem("Humpty Dumpty", listOf(
            "Humpty Dumpty sat on a wall,", "Humpty Dumpty had a great fall.",
            "All the king's horses and all the king's men", "Couldn't put Humpty together again."
        )),
        RhymeItem("Jack and Jill", listOf(
            "Jack and Jill went up the hill", "To fetch a pail of water.",
            "Jack fell down and broke his crown,", "And Jill came tumbling after."
        )),
        RhymeItem("Row Row Row Your Boat", listOf(
            "Row, row, row your boat,", "Gently down the stream.",
            "Merrily, merrily, merrily, merrily,", "Life is but a dream."
        )),
        RhymeItem("London Bridge", listOf(
            "London Bridge is falling down,", "Falling down, falling down.",
            "London Bridge is falling down,", "My fair lady."
        )),
        RhymeItem("If You're Happy", listOf(
            "If you're happy and you know it, clap your hands!",
            "If you're happy and you know it, clap your hands!",
            "If you're happy and you know it,", "Then your face will surely show it.",
            "If you're happy and you know it, clap your hands!"
        )),
        RhymeItem("Old MacDonald", listOf(
            "Old MacDonald had a farm, E-I-E-I-O!",
            "And on that farm he had a cow, E-I-E-I-O!",
            "With a moo-moo here and a moo-moo there,",
            "Here a moo, there a moo, everywhere a moo-moo.",
            "Old MacDonald had a farm, E-I-E-I-O!"
        )),
        RhymeItem("Five Little Monkeys", listOf(
            "Five little monkeys jumping on the bed,",
            "One fell off and bumped his head!",
            "Mama called the doctor and the doctor said,",
            "No more monkeys jumping on the bed!"
        )),
        RhymeItem("Wheels on the Bus", listOf(
            "The wheels on the bus go round and round,",
            "Round and round, round and round.",
            "The wheels on the bus go round and round,", "All through the town."
        ))
    )

    val storyItems: List<StoryItem> = listOf(
        StoryItem("The Tortoise and the Hare", listOf(
            "Once upon a time, a hare and a tortoise lived in a forest.",
            "The hare was very proud of his speed and always made fun of the slow tortoise.",
            "One day, the tortoise challenged the hare to a race. The hare laughed but agreed.",
            "The race began! The hare ran very fast and soon left the tortoise far behind.",
            "The hare thought, 'I'm so fast, I can take a nap and still win!'",
            "The tortoise kept walking slowly but steadily, never stopping.",
            "When the hare woke up, he saw the tortoise near the finish line!",
            "The tortoise crossed the finish line first and won the race!"
        ), "Slow and steady wins the race."),
        StoryItem("The Lion and the Mouse", listOf(
            "A mighty lion was sleeping when a little mouse ran over his nose.",
            "The lion caught the mouse and was about to eat it.",
            "The mouse cried, 'Please let me go! One day I may help you!'",
            "The lion laughed but let the mouse go free.",
            "Days later, the lion got caught in a hunter's net and roared for help.",
            "The little mouse heard the lion and ran to help.",
            "The mouse chewed through the ropes and set the lion free!",
            "The lion thanked the mouse. Even the smallest friend can be a great help!"
        ), "No act of kindness is ever wasted."),
        StoryItem("The Thirsty Crow", listOf(
            "One hot summer day, a crow was very thirsty and looking for water.",
            "He flew everywhere but couldn't find any water to drink.",
            "Finally, he saw a pot with a little water at the bottom.",
            "But his beak couldn't reach the water!",
            "The crow looked around and saw some small pebbles nearby.",
            "He picked up the pebbles one by one and dropped them into the pot.",
            "Slowly, the water level rose to the top!",
            "The crow drank the water and flew away happily. Being smart solves problems!"
        ), "Where there is a will, there is a way.")
    )

    val phonicsItems: List<PhonicsItem> = ('A'..'Z').map { letter ->
        PhonicsItem(
            letter.toString(),
            letter.toString().lowercase(),
            when (letter) {
                'A' -> listOf("Apple", "Ant", "Arrow")
                'B' -> listOf("Ball", "Bat", "Bus")
                'C' -> listOf("Cat", "Cup", "Car")
                'D' -> listOf("Dog", "Duck", "Drum")
                'E' -> listOf("Egg", "Elephant", "Eye")
                'F' -> listOf("Fish", "Fan", "Fox")
                'G' -> listOf("Goat", "Girl", "Green")
                'H' -> listOf("Hat", "Hen", "House")
                'I' -> listOf("Ice", "Ink", "Iron")
                'J' -> listOf("Jam", "Jar", "Jet")
                'K' -> listOf("Key", "King", "Kite")
                'L' -> listOf("Lamp", "Lion", "Log")
                'M' -> listOf("Man", "Moon", "Milk")
                'N' -> listOf("Net", "Nose", "Nest")
                'O' -> listOf("Owl", "Ox", "Orange")
                'P' -> listOf("Pen", "Pot", "Pig")
                'Q' -> listOf("Queen", "Quiz", "Quiet")
                'R' -> listOf("Rat", "Red", "Ring")
                'S' -> listOf("Sun", "Sit", "Six")
                'T' -> listOf("Top", "Ten", "Tap")
                'U' -> listOf("Umbrella", "Up", "Uncle")
                'V' -> listOf("Van", "Vet", "Vase")
                'W' -> listOf("Wolf", "Water", "Wind")
                'X' -> listOf("Box", "Fox", "Six")
                'Y' -> listOf("Yak", "Yarn", "Yell")
                'Z' -> listOf("Zoo", "Zip", "Zero")
                else -> listOf("Unknown")
            }
        )
    }

    val vocabularyItems: List<VocabularyItem> = listOf(
        VocabularyItem("Happy", "Feeling pleasure", "I am happy today", 1),
        VocabularyItem("Sad", "Feeling unhappy", "She looks sad", 1),
        VocabularyItem("Big", "Of large size", "The elephant is big", 1),
        VocabularyItem("Small", "Of little size", "The mouse is small", 1),
        VocabularyItem("Fast", "Moving quickly", "The car is fast", 1),
        VocabularyItem("Slow", "Moving at low speed", "The turtle is slow", 1),
        VocabularyItem("Beautiful", "Very pretty", "The flower is beautiful", 2),
        VocabularyItem("Brave", "Not afraid", "The firefighter is brave", 2),
        VocabularyItem("Curious", "Wanting to learn", "The child is curious", 2),
        VocabularyItem("Gentle", "Soft and kind", "She has a gentle voice", 2),
        VocabularyItem("Enormous", "Very very big", "The whale is enormous", 3),
        VocabularyItem("Delicious", "Very tasty", "The cake is delicious", 3),
        VocabularyItem("Fascinating", "Very interesting", "Space is fascinating", 3),
        VocabularyItem("Magnificent", "Very beautiful", "The palace is magnificent", 3),
        VocabularyItem("Adventurous", "Liking danger and excitement", "The explorer is adventurous", 4)
    )
}