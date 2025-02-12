public class Storyteller<T> implements House.Visitor<T> {
    String backstory = "GASP! But  how did we get to this point? Once upon a time there was a little wolf. " +
            "He was a kind wolf who had loving parents who cared for him dearly. " +
            "On this particular day, the little wolf was about to start his first day of school. " +
            "His parents waved him goodbye as he strolled under a bright autumnal sun, " +
            "a beam of joy spread across his face. He was ready to embark on his journey of education. " +
            "A storm had set in by the time he returned from school, the rain lashing from the skies as heavy as a whip. " +
            "Once he'd arrived home, he was soaked to the bone. He reached to open the door before realising it was ajar. " +
            "He pushed the door open and carefully stepped over the threshold. Pure silence was draped over the walls. " +
            "There, sprawled across the floor were his parents surrounded by the debris of what had once been the front room. " +
            "He dropped his school bag and croutched next to his parents, tears forming in his eyes. " +
            "He tried to shake the both of them awake, but they did not stir. Through his tears the little wolf suddenly noticed the words on the wall. " +
            "\"Death to the Dogs\" had been written in his parent's blood. Underneath these words, they had painted a pig surrounded by a flame. " +
            "The Order of the Orange Pigs (OOP). As the little wolf held his parents close he swore that day that the OOP would pay for what they'd done. " +
            "We now return to the present. \nAfter a baker's dozen years of searching, meeting with contacts, and killing minions he had found the ring leader. " +
            "The bastard's name was ";

    String middle = "Luckily the wolf had been training for this very day. He knocked on the door three times. Open up. "



    public T visit(StrawHouse house) {
        return backstory + house.occupant.name + " and they lived in the Straw House. "
    }


}