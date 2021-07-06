library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity ALU_4bit is
port(
	S:in std_logic_vector(3 downto 0);
	A,B:in std_logic_vector(7 downto 0);
	F:out std_logic_vector(7 downto 0);
	Cout:out std_logic;
	overflow:out std_logic
);
end ALU_4bit;

architecture bhv of ALU_4bit is
signal Ain,Bin,F_out: std_logic_vector(8 downto 0);
signal Aov,Bov,F_ov:std_logic_vector(8 downto 0);
signal A0:std_logic_vector(15 downto 0);--8bit mul4bit
begin
	process(S,A,B)
		variable q1:std_logic_Vector(15 downto 0);
		begin
		Ain<='0'&A;  
		Bin<='0'&B;
		Aov<=A(7)&A;
		Bov<=B(7)&B;
		--mul
		A0<="00000000"&A;--16bit
		q1:=(others=>'0');
		case S is
			when "0000"=>F_out<=Ain+Bin;
							 F_ov<=Aov+Bov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0001"=>F_out<=Ain-Bin;
							 F_ov<=Aov-Bov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0010"=>F_out<=Ain and Bin;
							 F_ov<=Aov and Bov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0011"=>F_out<=Ain or Bin;
							 F_ov<=Aov or Bov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0100"=>F_out<=not Ain;
							 F_ov<=not Aov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0101"=>F_out<=Ain NAND Bin; 
							 F_ov<=Aov NAND Bov; 
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0110"=>F_out<=Ain NOR Bin;
							 F_ov<=Aov NOR Bov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "0111"=>F_out<=Ain XOR Bin;
							 F_ov<=Aov XOR Bov;
							 F<=F_out(7 downto 0);
							 Cout<=F_out(8);
							 overflow<=F_ov(8) xor F_ov(7);
			when "1000"=>
							for i in 0 to 7 loop
								if (B(i)='1') then 
									q1:=q1+to_stdlogicvector(to_bitvector(A0)SLL(i));
								end if;
							end loop;
							F_out<=q1(8 downto 0);
							F<=F_out(7 downto 0);
							Cout<=F_out(8);
							overflow<=(Ain(7) xor Bin(7))or(Ain(6) xor Bin(6))or(Ain(5) xor Bin(5))or(Ain(4) xor Bin(4));
			when others =>F_out<=(others=>'0');
							 F_ov<=(others=>'0');
							 F<=F_out(7 downto 0);
							 cout<='0';
							 overflow<='0';
							 
		end case;
	end process;
end bhv;