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
import java.util.HashMap;
import java.util.Map;
import modelo.Indv;
import modelo.MonoGeneticAlgoritm;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 *
 * @author DiegoAndres
 */
public class PokeTAC {
    private static final String FILE_DIR = "Files/";
    public static final int MAX_POKEMON = 4; //Cantidad de pokemons por entrenador
    public static final int MAX_MOVES = 4; //Cantidad de moviminetos por pokemon
    public static final int MAX_DEPTH = 4;
    private MinMaxAlgo mmAlgo;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {       
        MainWindow mw = new MainWindow();
        mw.setVisible(true);        
        
        //MonoGeneticAlgoritm mga = new MonoGeneticAlgoritm(3, 0.1, -1, 1, 5);

        //mga.CreateInitialPopulation(4,10);

        //Indv[] ans = mga.ProccessGenerations(100);
    }
    
    static boolean askIfBattleAgain()
    {
        System.out.println("Deseas jugar otra vez? [s/n]:");
        
        String ans = System.console().readLine();
        
        return ans.equalsIgnoreCase("s");
    }
    
    // << InternalFields >>
    List<PokeInfo> pokemonDB;
    
    Battle activeBattle;
    Trainer userTrainer;
    Trainer aiTrainer;
    
    Random rnd;
    List<PokeType> listTypes;
    
    // << InternalMethods >>
    
    public PokeType FindPokeTypeByName(String name)
    {
        PokeType match = null;
        for(PokeType pt2 : listTypes)
        if (pt2.getName().equalsIgnoreCase(name)) {   match = pt2;   break; }
        return match;
    }
    
    public void initGame(String username)
    {
        initGame(username,"IA");       
    }
    public void initGame(String trainer_0_name, String trainer_1_name)
    {

        //load the list of maps of every type and its multipliers
        listTypes = null;
        try {
            listTypes=loadDataPokemonMultipliers();
        }
        catch(IOException e){
            System.out.println("error:PokeMultiplier loader");
            e.printStackTrace();
            
        }
        
                //Cargar datos de archivo
        try {
            loadDataPokeInfo();
        }
        catch(IOException e){
            System.out.println("error:PokeInfo loader");
            e.printStackTrace();
            
        }
        
        
        //for each pokemon on pokemonDB we set its movements and multipliers
        for(int i=0;i<pokemonDB.size();i++){
            
            try {
                    loadDataPokeMovements(pokemonDB.get(i));
                }
                catch(IOException e){
                    System.out.println("error:PokeMovements loader");
                    e.printStackTrace();
                }
            ////Intercambiar tipos por los cargados en loadDataPokemonMultipliers
            //List<PokeType> types=pokemonDB.get(i).getPokeTypes();
            //List<PokeType> newTypes = new ArrayList();
            //for(PokeType pt : types)
            //{
            //    newTypes.add(FindPokeTypeByName(pt.getName()));
            //}
            //pokemonDB.get(i).setTipos(newTypes);
            
        }
        //Crear todo lo necesario para el AI
        //createAI();
        
        userTrainer = new Trainer(trainer_0_name);
        aiTrainer = new Trainer(trainer_1_name);
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
                single_type = FindPokeTypeByName(arr_types[j]);
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
        BufferedImage imagen = null;
        //ImageIcon imagen=null;
        for (int i = 0; i < MAX_POKEMON; i++) {
            
            PokeInfo pokemon = pokemonDB.get(rnd.nextInt(pokemonDB.size()));
           
            //try {
            //    loadDataPokeMovements(pokemon);
            //}
            //catch(IOException e){
            //    System.out.println("error:PokeMovements loader");
            //    e.printStackTrace();
            //}
            
            List<Movement> moves = new ArrayList<>();
            for (int j = 0; j < MAX_MOVES; j++) {
                
                List<Movement> pokeMoves = pokemon.getMoves();
                
                Movement move = pokeMoves.get(rnd.nextInt(pokeMoves.size()));              
                
                moves.add(move); //TODO: Falta verificar que no se repita
            }
            ///////////////////////////////////////////////
            
//            imagen=null;
//            String route=FILE_DIR;
//            String fname="f_"+pokemon.getNombre();
//            route=route+fname+".gif";
//            imagen=new ImageIcon(route);
            
            /////////////////////////////////////////////////
            try {
                    imagen=null;
                    String route=FILE_DIR;
                    String fname="f_"+pokemon.getNombre();
                    route=route+fname+".gif";
                    imagen = ImageIO.read(new File(route));
                    
            } catch (IOException e) {
                System.out.println("error:PokeImageAI loader"+pokemon.getNombre());
                e.printStackTrace();
            }
            pokemon.setImagen(imagen);
            
            ipokemons.add(new Pokemon(pokemon,moves));
            
            
        }
        
        aiTrainer.setTeam(ipokemons);
    }
    
    private void initBattle()
    {
        activeBattle = new Battle(userTrainer,aiTrainer);
    }
 
    public Movement selectAIMove()
    {
        return setAIRandomMove(aiTrainer);
    }
    
    public Movement setAIRandomMove(Trainer trainer)
    {
        int counter = 0;
        for(Pokemon p : trainer.getTeam()){
            if(p.getHitPoints()>0) counter++;
        }
        
        if (counter>1 && rnd.nextBoolean())
        {
            //CambiarPokemon
            int index = rnd.nextInt(MAX_POKEMON);
            Pokemon selPoke = null;
            while(index == trainer.getActivePokemonIndex() || selPoke == null || selPoke.getHitPoints()==0){
                index = rnd.nextInt(MAX_POKEMON);
                selPoke = trainer.getTeam().get(index);
            }
            trainer.changePokemon(index);
            return null;
        }
        else
        {
            //Escojer ataque
            Movement m = trainer.getActivePokemon().getMovimientos().get(rnd.nextInt(MAX_MOVES));
            trainer.setNextMove(m);
            return m;
        }     
    }
        
    public Movement selectAIMinMaxMove(){
        PokeState state  = (PokeState)mmAlgo.getNextMove(new PokeState(new Battle(activeBattle)), true);
        if(state.getChosenMove()!= null){
            aiTrainer.setNextMove(state.getChosenMove());
            return state.getChosenMove();
        }else{
            aiTrainer.changePokemon(state.getBattle().getEntrenadores().get(1).getActivePokemonIndex());
            return null;
        }
        
    }
    
    public void setMinMaxWeightedMove(Battle battle)
    {
        PokeState state  = (PokeState)mmAlgo.getNextMove(new PokeState(new Battle(battle)), true);
        if(state.getChosenMove()!= null){
            battle.activeTrainer().setNextMove(state.getChosenMove());
        }else{
            battle.activeTrainer().changePokemon(state.getBattle().inactiveTrainer().getActivePokemonIndex());
        }
    }
    
    private List<PokeType> loadDataPokemonMultipliers() throws IOException{
        FileReader reader;
        File arch=null;
        String route=FILE_DIR;
        String fname="types.txt";
        String line;
        BufferedReader br;
        arch=new File(route + fname);
        reader=new FileReader(arch);
        br=null;
        br=new BufferedReader(reader);
        String[]values;
        int max_types=18;
        String d_value;
        double value;
        List<PokeType> pokeTypes = new ArrayList();
        for(int i=0;i<max_types;i++){
            line=br.readLine();
            values=line.split(" ");     
            pokeTypes.add(new PokeType(values[0]));
        }
        reader.close();
        

        arch=new File(route + fname);
        reader=new FileReader(arch);
        br=null;
        br=new BufferedReader(reader);
        //double[] converted_value;
        List<Map<PokeType,Double>> listMap= new ArrayList();
        //List<PokeType>thiPokeType=pokemon.getPokeTypes();
        for(int i=0;i<max_types;i++){
            line=br.readLine();
            values=line.split(" ");
            //converted_value=new double[values.length];
            Map<PokeType,Double> mapper = new HashMap();            
            for(int j=1;j<values.length;j++){//start at index 1 since the index 0 is this type 
                d_value=values[j].substring(((values[j].length())-4), values[j].length()-1);//haya el valor a convertir en double
                String key = values[j].substring(0, values[j].length()-5);//haya el valor a convertir en double
                value = Double.parseDouble(d_value);
                
                PokeType match = null;
                for(PokeType pt : pokeTypes)
                {
                    if (pt.getName().equalsIgnoreCase(key))
                    {
                        match = pt;
                        break;
                    }
                }
                                
                mapper.put(match,value);
            }
            listMap.add(mapper);//una lista con todos los los maps de multiplicadores
            
        }
        reader.close();        
        
        for (int i = 0; i < pokeTypes.size(); i++) {
            pokeTypes.get(i).SetMultiplicators(listMap.get(i));
        }
        
        return pokeTypes;    
    }
    
    public void loadDataPokeMovements(PokeInfo pokemon) throws IOException{
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
            single_type= FindPokeTypeByName(line2);
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
        BufferedImage imagen = null;
        //ImageIcon imagen=null;
        for(int i=0;i<pokemons.size();i++){
            ///////////////////////////////////////////////
            
//            imagen=null;
//            String route=FILE_DIR;
//            String fname="b_"+pokemons.get(i).getPokeInfo().getNombre();
//            route=route+fname+".gif";
//            imagen=new ImageIcon(route);
            
            /////////////////////////////////////////////////
            
            try {
                    imagen=null;
                    String route=FILE_DIR;
                    String fname="b_"+pokemons.get(i).getPokeInfo().getNombre();
                    route=route+fname+".gif";
                    imagen = ImageIO.read(new File(route));
                    
            } catch (IOException e) {
                System.out.println("error:PokeImage loader"+pokemons.get(i).getNombre());
                e.printStackTrace();
            }
            pokemons.get(i).getPokeInfo().setImagenB(imagen);
        }
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
    
    List<Pokemon> autoBattleTeam;
    public void prepareAutoBattleTeam()
    {
        selectAITeam();
        autoBattleTeam = aiTrainer.getTeam();
        aiTrainer.setTeam(null);        
    }
    
    
    
    public int weightedAutoBattle(double[] chromosomeA, double[] chromosomeB) 
    {    
        //Copiar equipos
        List<Pokemon> team1 = new ArrayList<>();
        List<Pokemon> team2 = new ArrayList<>();
        for (int i = 0; i < autoBattleTeam.size(); i++) {
            team1.add(new Pokemon(autoBattleTeam.get(i)));
            team2.add(new Pokemon(autoBattleTeam.get(i)));
        }
        userTrainer.setTeam(team1);
        aiTrainer.setTeam(team2);
        
        //Establecer pesos
        userTrainer.setWeights(chromosomeA);
        aiTrainer.setWeights(chromosomeB);
        
        //Iniciar batalla
        activeBattle = new Battle(userTrainer,aiTrainer);
        
        //Ejecutar batalla
        int maxTurns = 25;
        int countTurns = 1;
        while (!activeBattle.isBattleOver())
        {
            //En caso se halla llegado a un bucle infinito
            if ((countTurns%maxTurns)==0) 
                setAIRandomMove(activeBattle.activeTrainer());
            else
                setMinMaxWeightedMove(activeBattle);
            //En caso el bucle no se soluciona
            if (countTurns==60)
                return -1;
            
            activeBattle.proccessTurnLogic();
            
            countTurns++;
        }
        
        //Regresar ganador
        if (activeBattle.getLoser()!=userTrainer) return 0; else return 1;
    }
    
    public Trainer getAITrainer(){
        return aiTrainer;
    }
    
}
