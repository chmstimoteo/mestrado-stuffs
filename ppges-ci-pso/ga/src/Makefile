# Autor: Cristóvão Zuppardo Rufino
# Disciplina: Inteligência Computacional

CC := gcc

LIBS := -lm	
CFLAGS := 

RM := rm

EXEC := GA

OBJ := $(shell ls *.c | sed -e 's/\.c/\.o/g')

all: $(OBJ)
	@echo "Compilando executável..."
	$(CC) $(LIBS) $(CFLAGS) -o $(EXEC) $(OBJ) -Wall
	@echo "Done!"

clean:
	$(RM) -rf $(OBJ) $(EXEC)

