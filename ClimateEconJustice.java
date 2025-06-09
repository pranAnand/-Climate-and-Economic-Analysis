package climate;
import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {
        String[] str = inputLine.split(",");
        String state = str[2];

        if (firstState == null) {
            StateNode s = new StateNode(state, null, null);
            firstState = s;
        }
        boolean bool = true;
        StateNode statePtr = firstState;

        while(statePtr != null) {
            if (statePtr.getName().equals(state)) {
                bool = false;
                return; //do nothing
            }
            statePtr = statePtr.getNext(); //point next
        }

        if (bool == true) {
            statePtr = firstState;
            StateNode l = new StateNode(state, null, null);
            while (statePtr.getNext() != null) {
                statePtr = statePtr.getNext();
            }
            statePtr.setNext(l);
        }
    }

  

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {
        String str[] = inputLine.split(","); //read line
        String countyName = str[1]; //index 1 for county Name
        String stateName = str[2]; //index 2 for state Name
        
        StateNode statePtr = firstState;
        boolean isStateFound = false;

        while (statePtr != null) {
            if (statePtr.getName().equals(stateName)) { 
                isStateFound = true;
                break; //terminate loop once found, if found.
            }
            else {
                statePtr = statePtr.getNext();
            }
        }
        if (isStateFound == true) {
            CountyNode countyPtr = statePtr.getDown(); //point state down to county once found
            CountyNode prevCounty = null;
            boolean isCountyFound  = false;

            while (countyPtr != null) {
                if (countyPtr.getName().equals(countyName)) {
                    isCountyFound = true;
                    break;
                }
                else {
                    prevCounty = countyPtr;
                    countyPtr = countyPtr.getNext();
                }
            }
            //if county not found
            if (isCountyFound == false) {
                CountyNode county = new CountyNode(countyName, null, null);
                if (prevCounty == null) { //if this is the first county, set down
                    statePtr.setDown(county); 
                }
                else {
                    prevCounty.setNext(county); //else set next
                }
            }
        }
    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {
        String str[] = inputLine.split(",");
        String stateName = str[2];
        String countyName = str[1];
        String communityID = str[0];
        
        Data dataCommunity = new Data(Double.parseDouble(str[3]), Double.parseDouble(str[4]), Double.parseDouble(str[5]), 
                            Double.parseDouble(str[8]), Double.parseDouble(str[9]), str[19], 
                            Double.parseDouble(str[49]), Double.parseDouble(str[37]), Double.parseDouble(str[121]));

        StateNode statePtr = firstState;
        boolean isStateFound = false;

        CountyNode countyPtr = statePtr.getDown();

        while (statePtr != null) {
            if ((statePtr.getName().equals(stateName))) { 
                isStateFound = true;
                countyPtr = statePtr.getDown(); //start the county list once state is found
                break; //terminate loop once found, if found.
            }
            else {
                statePtr = statePtr.getNext();
            }
        }
        if (isStateFound == true) {
            countyPtr = statePtr.getDown(); //Point state down to county once found
            CountyNode prevCounty = null;
            boolean isCountyFound  = false;

            while (countyPtr != null) {
                if (countyPtr.getName().equals(countyName)) {
                    isCountyFound = true;
                    break;
                }
                else {
                    prevCounty = countyPtr;
                    countyPtr = countyPtr.getNext();
                }
            }
            //if county not found
            if (isCountyFound == false) {
                CountyNode county = new CountyNode(countyName, null, null);
                if (prevCounty == null) { //if this is the first county, set down
                    statePtr.setDown(county); 
                }
                else {
                    prevCounty.setNext(county); //else set next
                }
            }
            if (isCountyFound == true) {
                CommunityNode communityPtr = countyPtr.getDown();
                CommunityNode prevCommunity = null;
                boolean isCommunityFound = false;
                while (communityPtr != null) {
                    if (communityPtr.getName().equals(communityID)) {
                        isCommunityFound = true;
                        break;
                    }
                    else {
                        prevCommunity = communityPtr;
                        communityPtr = communityPtr.getNext();
                    }
                }
                if (isCommunityFound == false) {
                    CommunityNode community = new CommunityNode(communityID, null, dataCommunity);
                    if (prevCommunity == null) {
                        countyPtr.setDown(community);
                    }
                    else {
                        prevCommunity.setNext(community);
                    }
                }
            }
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {
        StateNode statePtr = firstState;
        int counter = 0;
        
        while (statePtr != null) {
            CountyNode countyPtr = statePtr.getDown();
            while (countyPtr != null) {
                CommunityNode communityPtr = countyPtr.getDown();
                while (communityPtr != null) {
                    if (communityPtr.getInfo().getAdvantageStatus().equals("True") && communityPtr.getInfo() != null) {
                        if (race.equals("African American")) {
                            if (communityPtr.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("Native American")) {
                            if (communityPtr.getInfo().getPrcntNative() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("Asian American")) {
                            if (communityPtr.getInfo().getPrcntAsian() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("White American")) {
                            if (communityPtr.getInfo().getPrcntWhite() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("Hispanic American")) {
                            if (communityPtr.getInfo().getPrcntHispanic() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                    }
                   communityPtr = communityPtr.getNext();
                }
                countyPtr = countyPtr.getNext();
            }
            statePtr = statePtr.getNext();
        }
        return counter;
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {
        StateNode statePtr = firstState;
        int counter = 0;
        
        while (statePtr != null) {
            CountyNode countyPtr = statePtr.getDown();
            while (countyPtr != null) {
                CommunityNode communityPtr = countyPtr.getDown();
                while (communityPtr != null) {
                    if (communityPtr.getInfo().getAdvantageStatus().equals("False") && communityPtr.getInfo() != null) {
                        if (race.equals("African American")) {
                            if (communityPtr.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("Native American")) {
                            if (communityPtr.getInfo().getPrcntNative() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("Asian American")) {
                            if (communityPtr.getInfo().getPrcntAsian() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("White American")) {
                            if (communityPtr.getInfo().getPrcntWhite() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                        if (race.equals("Hispanic American")) {
                            if (communityPtr.getInfo().getPrcntHispanic() * 100 >= userPrcntage) {
                                counter++;
                            }
                        }
                    }
                   communityPtr = communityPtr.getNext();
                }
                countyPtr = countyPtr.getNext();
            }
            statePtr = statePtr.getNext();
        }
        return counter;
    }   
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {
        StateNode statePtr = firstState;
        ArrayList <StateNode> list = new ArrayList<StateNode>();
        while (statePtr != null) {
            CountyNode countyPtr = statePtr.getDown();
            while (countyPtr != null) {
                CommunityNode communityPtr = countyPtr.getDown();
                while (communityPtr != null) {
                    //check if each community has a recorded pollution level equal to or higher than the pollution level entered.
                    if (communityPtr.getInfo().getPMlevel() >= PMlevel && communityPtr != null) {
                        if (!list.contains(statePtr)) { //prevent printing the same state
                            list.add(statePtr); //add state
                        }
                    }
                    communityPtr = communityPtr.getNext();
                }
                countyPtr = countyPtr.getNext();
            }
            statePtr = statePtr.getNext();
        }
        return list;
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {
        StateNode statePtr = firstState;
        int counter = 0;
        
        while (statePtr != null) {
            CountyNode countyPtr = statePtr.getDown();
            while (countyPtr != null) {
                CommunityNode communityPtr = countyPtr.getDown();
                while (communityPtr != null) {
                    if (communityPtr.getInfo().getChanceOfFlood() >= userPercntage && communityPtr != null) {
                        counter++;
                    }
                    communityPtr = communityPtr.getNext();
                }
                countyPtr = countyPtr.getNext();
            }
            statePtr = statePtr.getNext();
        }
        return counter;
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {
        StateNode statePtr = firstState;
        ArrayList<CommunityNode> list = new ArrayList<>();
        
        while (statePtr != null && !statePtr.getName().equals(stateName)) {
            statePtr = statePtr.getNext(); //go next if not state not found
        }
        if (statePtr != null && statePtr.getName().equals(stateName)) { 
            CountyNode countyPtr = statePtr.getDown(); //once state is found go down layered structure
            while (countyPtr != null) {
                CommunityNode communityPtr = countyPtr.getDown();
                while (communityPtr != null) {
                    if (list.size() < 10) { 
                        list.add(communityPtr); //get 10 communities
                    }
                    else {
                        int index = 0;
                        CommunityNode c = list.get(0);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getInfo().getPercentPovertyLine() < c.getInfo().getPercentPovertyLine()) {
                                c = list.get(i);
                                index = i;
                            }
                        }
                        if (communityPtr.getInfo().getPercentPovertyLine() > c.getInfo().getPercentPovertyLine()) {
                            list.set(index, communityPtr); //list.set(index, elementToReplaceWith)
                        }
                    }
                    communityPtr = communityPtr.getNext(); 
                }
                countyPtr = countyPtr.getNext();
            }
        }
        return list;
    }
}