# Autor: Cristóvão Zuppardo Rufino
# Disciplina: Inteligência Computacional

CC := gcc

LIBS := -lm	
CFLAGS := -ggdb

RM := rm

EXEC := PSO

OBJ := $(shell ls *.c | sed -e 's/\.c/\.o/g')

all: $(OBJ)
	@echo "Compilando executável..."
	$(CC) $(LIBS) $(CFLAGS) -o $(EXEC) $(OBJ) -Wall
	@echo "Done!"

clean:
	$(RM) -rf $(OBJ)
	$(RM) -rf $(EXEC)

