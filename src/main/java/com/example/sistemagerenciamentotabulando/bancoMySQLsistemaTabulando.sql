# create database sistemaTabulandoProjeto;

use sistemaTabulandoProjeto;

create table horario(
    id_horario int primary key auto_increment,
    data_horario date not null,
    turno varchar(30),
    hora varchar(10),
    nome_monitor varchar(100),
    status_horario varchar(20)
);

create table visitante(
	matricula integer primary key,
    nome varchar(100),
    idade integer,
    genero char(2) check (genero in ('M', 'F', 'NB', 'O', '-')),
    nivel_ensino varchar(15) check (nivel_ensino in ('Integrado', 'Superior', 'Não-estudante')),
    curso varchar(100),
    turno varchar(15) check (turno in ('Matutino', 'Vespertino', 'Noturno', 'Não-estudante')),
    possuiNEE boolean
);

DROP TABLE jogo;

create table jogo(
	id_jogo integer auto_increment primary key,
    titulo varchar(100),
    tipo varchar(30),
    min_numero_jogadores integer,
    max_numero_jogadores integer,
    descricao varchar(100),
    marca varchar(15),
    faixaEtaria integer,
    tempo_partida integer, # em minutos
    disponibilidade boolean
);

create table frequencia(
	id_frequencia integer primary key auto_increment,
    id_visitante integer,
    id_horario integer,
    foreign key (id_visitante) references visitante(matricula),
    foreign key (id_horario) references horario(id_horario)
);

create table uso_jogo(
	id_uso_jogo integer primary key auto_increment,
    id_jogo integer,
    id_horario integer,
    foreign key (id_jogo) references jogo(id_jogo),
    foreign key (id_horario) references horario(id_horario)
);

select * from horario;

select * from visitante;

select * from jogo;

-- selecionar a frequência dos horários
SELECT 
    f.id_frequencia,
    v.nome AS nome_visitante,
    v.matricula,
    h.data_horario,
    h.turno,
    h.nome_monitor
FROM frequencia f
JOIN visitante v ON f.id_visitante = v.matricula
JOIN horario h ON f.id_horario = h.id_horario;

-- selecionar os jogos utilizados nos horários
SELECT 
    uj.id_uso_jogo,
    j.titulo AS nome_jogo,
    j.tipo,
    h.data_horario,
    h.turno
FROM uso_jogo uj
JOIN jogo j ON uj.id_jogo = j.id_jogo
JOIN horario h ON uj.id_horario = h.id_horario;

