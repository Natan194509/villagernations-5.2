package com.example.villagernations;

import net.minecraft.nbt.CompoundTag;

/** Um edifício da vila: concluído (funcionando) ou em obra. progresso é em centésimos de unidade de obra. */
public class Edificio {
    public int id = 0;
    public TipoEdificio tipo = TipoEdificio.CASA;
    public boolean concluido = false;
    public int progresso = 0;

    public CompoundTag salvar() {
        CompoundTag t = new CompoundTag();
        t.putInt("id", id);
        t.putInt("tipo", tipo.ordinal());
        t.putBoolean("concluido", concluido);
        t.putInt("progresso", progresso);
        return t;
    }

    public static Edificio carregar(CompoundTag t) {
        Edificio e = new Edificio();
        e.id = t.getInt("id");
        e.tipo = TipoEdificio.porIndice(t.getInt("tipo"));
        e.concluido = t.getBoolean("concluido");
        e.progresso = t.getInt("progresso");
        return e;
    }
}
