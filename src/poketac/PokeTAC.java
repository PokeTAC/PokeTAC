/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poketac;

import View.MainWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelo.Battle;
import MinMaxAlgorithm.MinMaxAlgo;
import MinMaxAlgorithm.PokeState;
import modelo.Trainer;
import modelo.Pokemon;
import modelo.Movement;
import modelo.PokeInfo;
import modelo.PokeType;
import modelo.EffectInfo;
import java.io.*;
import java.util.Map;

/**
 *
 * @author DiegoAndres
 */
public class PokeTAC {
    private static final String FILE_DIR = "Files/";
    private MinMaxAlgo mmAlgo;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
        
//        System.out.println("Bienvenido a PokeTAC! =D");
//        
//        PokeTAC pokeTAC = new PokeTAC();
//        
//        //GUI: Pantalla de bienvenida
//        
//        pokeTAC.initGame("Perejil");
//             
//        //GUI: Iniciar Batalla
//        do 
//        {
//            pokeTAC.selectAITeam();
//            
//            //GUI: Seleccionar Team de usuario para la batalla
//            pokeTAC.askUserTeam();
//
//            pokeTAC.initBattle();
//            
//            //GUI: Verificar turno de batalla o si ha terminado
//            while (!pokeTAC.activeBattle.isBattlerOver())
//            {
//                
//                Trainer nextTrainer = pokeTAC.activeBattle.nextTrainer();
//
//                if (nextTrainer==pokeTAC.aiTrainer)
//                {
//                    pokeTAC.selectAIMove();
//                }
//                else
//                {
//                    //GUI: Seleccionar movimiento usuario
//                    pokeTAC.askUserMove();
//                }
//
//                pokeTAC.activeBattle.proccessTurnLogic();
//            }
//
//            //GUI: Mostrar pantalla resultado
//            
//            pokeTAC.endBattle();
//            
//            
//        } while (askIfBattleAgain());
//            
//        //GUI: Mostar pantalla despedida
//        
//        pokeTAC.endGame();
        
    }
    
    static boolean askIfBattleAgain()
    {
        System.out.println("Deseas jugar otra vez? [s/n]:");
        
        String ans = System.console().readLine();
        
        return ans.equalsIgnoreCase("s");
    }
    
    // << Constants >>
    
    public static final int MAX_POKEMON = 4; //Cantidad de pokemons por entrenador
    public static final int MAX_MOVES = 4; //Cantidad de moviminetos por pokemon
    public static final int MAX_DEPTH = 10;
    
    // << InternalFields >>
    List<PokeInfo> pokemonDB;
    
    Battle activeBattle;
    Trainer userTrainer;
    Trainer aiTrainer;
    
    Random rnd;
    
    // << InternalMethods >>
    
    public void initGame(String username)
    {
        //Cargar datos de archivo
        try {
            loadDataPokeInfo();
        }
        catch(IOException e){
            System.out.println("error:PokeInfo loader");
            e.printStackTrace();
            
        }
        //load the list of maps of every type and its multipliers
        List<Map<PokeType,Double>> listMaps=null;
        try {
            listMaps=loadDataPokemonMultipliers();
        }
        catch(IOException e){
            System.out.println("error:PokeMultiplier loader");
            e.printStackTrace();
            
        }
        List<PokeType>types;
        //for each pokemon on pokemonDB we set its movements and multipliers
        for(int i=0;i<pokemonDB.size();i++){
            
            try {
                    loadDataPokeMovements(pokemonDB.get(i));
                }
                catch(IOException e){
                    System.out.println("error:PokeMovements loader");
                    e.printStackTrace();
                }
            //assign each type its multiplier
            types=pokemonDB.get(i).getPokeTypes();
            for(int j=0;j<types.size();j++){
                for(int h=0;h<17;h++){
                    if(((listMaps.get(h)).get(types.get(j)))!=null){//it found the type on the listMap
                        types.get(j).SetMultiplicators(listMaps.get(h));
                        break;
                    }
                }
                
            }
            
        }
        //Crear todo lo necesario para el AI
        //createAI();
        
        userTrainer = new Trainer(username);
        aiTrainer = new Trainer("IA");
        mmAlgo = new MinMaxAlgo(MAX_DEPTH);
        
        rnd = new Random();
       
    }
    private void loadDataPokeInfo()throws IOException
    {
        //................ Cargar data pokeInfo
        pokemonDB = new ArrayList<PokeInfo>();
        FileReader reader;
        File arch=null;
        String line1,line2,line3;
        BufferedReader br;
        arch=new File(FILE_DIR + "pokedex.txt");
        reader=new FileReader(arch);
        br=null;
        br=new BufferedReader(reader);
        PokeInfo newPoke;
        ArrayList<PokeType> types;
        PokeType single_type;
        int numPokemon=20;
        String[]values;
        int[] converted_value;
        String[]arr_types;
        for(int i=0;i<numPokemon;i++){
            line1=br.readLine();
            line2=br.readLine();
            line3=br.readLine();
            newPoke= new PokeInfo();
            types=new ArrayList<>();
            newPoke.setNombre(line1);
            arr_types=line2.split("/");
            
            for(int j=0;j<arr_types.length;j++){
                single_type = new PokeType(arr_types[j]);
                types.add(single_type);
            }
            newPoke.setTipos(types);
            
            values=line3.split(" ");
            converted_value=new int[values.length];
            for(int k=0;k<values.length;k++){
                converted_value[k] = Integer.parseInt(values[k]);
            }
            newPoke.setHp(converted_value[0]);
            newPoke.setAtaque(converted_value[1]);
            newPoke.setDefensa(converted_value[2]);
            newPoke.setVelocidad(converted_value[5]);
            pokemonDB.add(newPoke);
        }
        reader.close();
        //................ fin Cargar data pokeInfo
    }
    
    //Aqui se selecciona los pokemons para la IA
    public void selectAITeam()
    {
        List<Pokemon> ipokemons = new ArrayList<>();
        
        for (int i = 0; i < MAX_POKEMON; i++) {
            
            PokeInfo pokemon = pokemonDB.get(rnd.nextInt(pokemonDB.size()));
           
            try {
                loadDataPokeMovements(pokemon);
            }
            catch(IOException e){
                System.out.println("error:PokeMovements loader");
                e.printStackTrace();
            }
            
            List<Movement> moves = new ArrayList<>();
            for (int j = 0; j < MAX_MOVES; j++) {
                
                List<Movement> pokeMoves = pokemon.getMoves();
                
                Movement move = pokeMoves.get(rnd.nextInt(pokeMoves.size()));              
                
                moves.add(move);
            }
            
            ipokemons.add(new Pokemon(pokemon,moves));
        }
                
        aiTrainer.setTeam(ipokemons);
    }
    
    private void initBattle()
    {
        activeBattle = new Battle(userTrainer,aiTrainer);
    }
 
    public void selectAIMove()
    {
        if (rnd.nextBoolean())
        {
            //CambiarPokemon
            aiTrainer.changePokemon(rnd.nextInt(MAX_POKEMON));
        }
        else
        {
            //Escojer ataque
            aiTrainer.setNextMove(aiTrainer.getActivePokemon().getMovimientos().get(MAX_MOVES-1));
        }     
    }
    
    public void selectAIMinMaxMove(){
        PokeState state  = (PokeState)mmAlgo.getNextMove(new PokeState(new Battle(activeBattle)), true);
        if(state.getChosenMove()!= null){
            aiTrainer.setNextMove(state.getChosenMove());
        }else{
            aiTrainer.changePokemon(state.getBattle().getEntrenadores().get(1).getActivePokemonIndex());
        }
        
    }
    
    private void endBattle()
    {
        activeBattle = null;
    }
    
    private void endGame()
    {
        
    }

    private void askUserMove() {
                
        if (rnd.nextBoolean())
        {
            //CambiarPokemon
            userTrainer.changePokemon(rnd.nextInt(MAX_POKEMON));
        }
        else
        {
            //Escojer ataque
            userTrainer.setNextMove(userTrainer.getActivePokemon().getMovimientos().get(MAX_MOVES));
        } 
        
    }

    private void askUserTeam() {
        
        List<Pokemon> ipokemons = new ArrayList<>();
        
        for (int i = 0; i < MAX_POKEMON; i++) {
            
            PokeInfo pokemon = pokemonDB.get(rnd.nextInt(pokemonDB.size()));
            
            List<Movement> moves = new ArrayList<>();
            try {
                loadDataPokeMovements(pokemon);
            }
            catch(IOException e){
                System.out.println("error:PokeMovements loader");
                e.printStackTrace();
            }
            for (int j = 0; j < MAX_MOVES; j++) {
                
                List<Movement> pokeMoves = pokemon.getMoves();
                
                Movement move = pokeMoves.get(rnd.nextInt(pokeMoves.size()));              
                
                moves.add(move);
            }
            
            ipokemons.add(new Pokemon(pokemon,moves));
        }
        
        userTrainer.setTeam(ipokemons);
                
    }
    private List<Map<PokeType,Double>> loadDataPokemonMultipliers() throws IOException{
        FileReader reader;
        File arch=null;
        String route=FILE_DIR;
        String fname="types.txt";
        String line;
        BufferedReader br;
        arch=new File(route);
        reader=new FileReader(arch);
        br=null;
        br=new BufferedReader(reader);
        String[]values;
        int max_types=17;
        PokeType single_type;
        String d_value;
        double value;
        //double[] converted_value;
        Map<PokeType,Double> mapper=null;
        List<Map<PokeType,Double>> listMap=null;
        //List<PokeType>thiPokeType=pokemon.getPokeTypes();
        for(int i=0;i<max_types;i++){
            line=br.readLine();
            values=line.split(" ");
            //converted_value=new double[values.length];
            single_type=new PokeType(values[0]);
            for(int j=1;j<values.length;j++){//start at index 1 since the index 0 is this type 
                d_value=values[j].substring(((values[j].length())-4), values[j].length()-1);//haya el valor a convertir en double
                value = Double.parseDouble(d_value);
                mapper.put(single_type,value);
            }
            listMap.add(mapper);//una lista con todos los los maps de multiplicadores
            
        }
        reader.close();
       return listMap;    
    }
    public static void loadDataPokeMovements(PokeInfo pokemon) throws IOException{
        FileReader reader;
        File arch=null;
        String route=FILE_DIR;
        String fname="a_"+pokemon.getNombre();
        route=route+fname+".txt";
        String line1,line2,line3;
        BufferedReader br;
        arch=new File(route);
        reader=new FileReader(arch);
        br=null;
        br=new BufferedReader(reader);
        int max_pokeMoves=8;
        Movement newMove;
        ArrayList<Movement> movements=new ArrayList<>();
        PokeType single_type;
        String[]values;
        int[] converted_value;
        int baseD,acc;
        EffectInfo effect = null;
        for(int i=0;i<max_pokeMoves;i++){
            line1=br.readLine();//name
            line2=br.readLine();//type
            line3=br.readLine();//values
            single_type=new PokeType(line2);
            values=line3.split(" ");
            converted_value=new int[values.length];
            if(values.length==3){//this means it have an effect
                for(int j=0;j<(values.length-1);j++){
                    converted_value[j]=Integer.parseInt(values[j]);
                }

                switch(values[values.length-1]){//last one on this line is the effect type
                    case "P":
                        effect= EffectInfo.Paralyze;
                        break;
                    case "D":
                        effect=EffectInfo.Sleep;
                        break;
                    case "E":
                        effect=EffectInfo.Poison;
                        break;
                }
                
            }
            else{//doesn't have an effect
                for(int j=0;j<(values.length);j++){
                    converted_value[j]=Integer.parseInt(values[j]);
                }
                effect=EffectInfo.None;
                
            }
            baseD=converted_value[0];
            acc=converted_value[1];
            newMove=new Movement(line1,baseD,acc,single_type,effect);
            movements.add(newMove);
        }
        reader.close();
        pokemon.setMoves(movements);
    }
    
    public void initBattle(List<Pokemon> pokemons){
        selectAITeam();
        userTrainer.setTeam(pokemons);
        initBattle();
    }
    
    public Battle getBattle(){
        return activeBattle;
    }
    
    public List<PokeInfo> getAllPokemons(){
        return pokemonDB;
    }
    
    public boolean isUserTurn(){
        return activeBattle.isUserTurn();
    }
    
    
}
