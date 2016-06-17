/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Franco
 */
   public class Indv
    {
        //#region << ExternalFields >>

        double[] chromosome;
        public double[] getChromosome()
        {
            return chromosome;
        }

        int fitness;
        public int getFitness()
        {
            return fitness;
        }
        public void setFirness(int value)
        {
            fitness = value;
        }

        //#endregion

        //#region << Constructors >>

        /// <summary>
        /// Crea un nuevo individuo con el cromosoma indicado
        /// </summary>
        /// <param name="chromosome"></param>
        public Indv(double[] chromosome)
        {
            this.chromosome = chromosome;
        }

        //#endregion


        //#region << ExternalMethods >>

        /// <summary>
        /// Cruza todos los cromosomas de este individuo con la pareja indicada y devuelve dos hijos resutados del cruce.
        /// </summary>
        public Indv[] Cross(Indv mate, int crossPoint)
        {
            double[] child1 = (double[])this.chromosome.clone();
            double[] child2 = (double[])mate.chromosome.clone();

            //Random rnd = new Random(); int crossPoint = rnd.Next(1, chromosome.Length);

            for (int i = crossPoint; i < chromosome.length; i++)
            {
                double temp = child1[i];
                child1[i] = child2[i];
                child2[i] = temp;
            }

            Indv[] childs = new Indv[2];
            childs[0] = new Indv(child1);
            childs[1] = new Indv(child2);

            return childs;
        }

        public String ToString()
        {
            String s = "";

            for(double gen : chromosome)
            {
                s += " " + gen;
            }

            s += " ";

            return s;
        }

        //#endregion


        //#region << InternalMethods >>

        //#endregion


    }

