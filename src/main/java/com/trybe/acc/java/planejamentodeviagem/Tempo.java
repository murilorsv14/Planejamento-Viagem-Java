package com.trybe.acc.java.planejamentodeviagem;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Tempo {
  private LocalDateTime embarque;
  private String origem;
  private String destino;
  private int duracao;

  private static final String formato = "dd/MM/yyyy HH:mm:ss";

  /**
   * Método construtor da classe.
   * 
   */
  public Tempo(String embarque, String origem, String destino, int duracao) {
    this.embarque = LocalDateTime.parse(embarque, DateTimeFormatter.ofPattern(formato));
    this.origem = origem;
    this.destino = destino;
    this.duracao = duracao;
  }

  /**
   * retonarDesembarqueHorarioLocalDestino.
   * 
   * Transforma todos os fusos horarios disponíveis na classe ZoneId em um array de Strings, onde
   * nos percorremos em busca do identificador do fuso horario do nosso destino, uma vez com esse
   * fuso horario, podemos recuperar o horario local de desembarque no nosso destino
   */
  public String retonarDesembarqueHorarioLocalDestino() {

    String[] fusosHorarios = new String[ZoneId.getAvailableZoneIds().size()];
    ZoneId.getAvailableZoneIds().toArray(fusosHorarios);
    List<String> arrayDeFusos = List.of(fusosHorarios);
    
    String fusoHorarioOrigem = arrayDeFusos.stream()
        .filter(fuso -> fuso.contains(origem))
        .collect(Collectors.toList())
        .get(0);

    ZoneId fusoHorarioIdOrigem = ZoneId.of(fusoHorarioOrigem);

    ZonedDateTime origemHorarioLocal = this.embarque.atZone(fusoHorarioIdOrigem);

    String fusoHorarioDestino = arrayDeFusos.stream()
    .filter(fuso -> fuso.contains(destino))
    .collect(Collectors.toList())
    .get(0);

    ZonedDateTime destinoHorarioLocal =
        origemHorarioLocal.withZoneSameInstant(ZoneId.of(fusoHorarioDestino));

    return destinoHorarioLocal.plusHours(this.duracao).format(DateTimeFormatter.ofPattern(formato));
  }

  /**
   * retonarDesembarqueHorarioLocalOrigem.
   * 
   */
  public String retonarDesembarqueHorarioLocalOrigem() {
    String[] fusosHorarios = new String[ZoneId.getAvailableZoneIds().size()];
    ZoneId.getAvailableZoneIds().toArray(fusosHorarios);

    int indiceFusoHorarioOrigem = 0;

    for (int i = 0; i < fusosHorarios.length; i++) {
      if (fusosHorarios[i].equals(origem)) {
        indiceFusoHorarioOrigem = i;
      }
    }

    String fusoHorarioOrigem = fusosHorarios[indiceFusoHorarioOrigem];
    ZoneId fusoHorarioIdOrigem = ZoneId.of(fusoHorarioOrigem);

    ZonedDateTime origemHorarioLocal = this.embarque.atZone(fusoHorarioIdOrigem);

    return origemHorarioLocal.plusHours(this.duracao).format(DateTimeFormatter.ofPattern(formato));
  }
}
